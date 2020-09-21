package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelData;
import com.antra.evaluation.reporting_system.pojo.report.ExcelDataHeader;
import com.antra.evaluation.reporting_system.pojo.report.ExcelDataSheet;
import com.antra.evaluation.reporting_system.repo.ExcelRepository;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import com.antra.evaluation.reporting_system.repo.ExcelRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    ExcelRepository excelRepository;
    private static final Logger log = LoggerFactory.getLogger(ExcelServiceImpl.class);

    public ExcelServiceImpl() {
        this.excelRepository = new ExcelRepositoryImpl();
    }

    /**
     *
     * @param id
     * Provide file deletion operation from database.
     */
    public ExcelResponse deleteRepo(String id){
        ExcelFile excelFile=excelRepository.deleteFile(id);
        String projectFolderStr = System.getProperty("user.dir");
        ExcelResponse response=new ExcelResponse();
        if(excelFile!=null){
            log.info("File deleted from database");
            String fileName=projectFolderStr+"\\"+excelFile.getFileName()+".xlsx".toString();
            try{
                File file=new File(fileName);
                file.delete();
            } catch(Exception exception){
                log.error("Files doesn't exist or in use for deleting from local");
            }
            response.setFileId(excelFile.getFileName());
        }
        else{
            response=null;
        }

        return response;
    }

    /**
     *Shows the list of the Files which is stored in the database.
     *
     */
    public List<ExcelResponse> showRepo(){
        List<ExcelFile> excelFiles=excelRepository.getFiles();
        List<ExcelResponse>  responses=new ArrayList<>();
        for(ExcelFile excelFile:excelFiles){
            ExcelResponse excelResponse=new ExcelResponse();
            excelResponse.setExcelFile(excelFile);
            excelResponse.setFileId(excelFile.getFileName());

            responses.add(excelResponse);
        }

        return responses;

    }

    /**
     *
     * @param file
     * Saving the Excel files to database operation.
     */
    public boolean saveToRepo(ExcelFile file){
        if(excelRepository.saveFile(file)!=null){
            return true;
        }
        return false;
    }

    /**
     *
     * @param request
     *
     * @throws IOException
     * Generates Excel File with using local time information.
     */
    public ExcelFile create(ExcelRequest request) throws IOException {

        List<ExcelDataHeader> headers = new ArrayList<ExcelDataHeader>();
        List<String> head= request.getHeaders();

        for (String myHead: head) {
            ExcelDataHeader header = new ExcelDataHeader();
            header.setName(myHead);
            header.setWidth(myHead.length());
            headers.add(header);
        }

        ExcelDataSheet sheet = new ExcelDataSheet();
        sheet.setTitle(request.getDescription());
        sheet.setRows(request.getData());
        sheet.setHeaders(headers);

        List<ExcelDataSheet> sheets = new ArrayList<ExcelDataSheet>();
        sheets.add(sheet);

        ExcelData excelData = new ExcelData();
        excelData.setSheets(sheets);
        excelData.setGeneratedTime(LocalDateTime.now());

        excelData.createName();

        ExcelFile excelFile=new ExcelFile();
        excelFile.setFileName(excelData.getName());
        excelFile.setSubmitter(request.getSubmitter());

        try{

            ExcelGenerationService excelGenerationServiceImpl=new ExcelGenerationServiceImpl();
            excelGenerationServiceImpl.generateExcelReport(excelData);
        }catch (Exception e){
            log.error("File creation unsuccessfull");
        }


        return excelFile;

    }

    /**
     *
     * @param request
     *
     * @throws IOException
     * Creates multiple sheets.
     */
    public ExcelFile multiCreate(MultiSheetExcelRequest request) throws IOException {
        String splitBy = request.getSplitBy();
        ExcelData excelData = new ExcelData();

        List<ExcelDataSheet> sheets = new ArrayList<ExcelDataSheet>();
        Set<String> sheetNames = new HashSet<String>();
        List<ExcelDataHeader> headers = new ArrayList<ExcelDataHeader>();
        int splitIndex = request.getHeaders().indexOf(splitBy);
        for (int i = 0; i < request.getHeaders().size(); i ++) {
            ExcelDataHeader header = new ExcelDataHeader();
            if (i != splitIndex) {
                header.setName(request.getHeaders().get(i));
                headers.add(header);
            }
            else{
                header.setName(splitBy);
                headers.add(header);
            }
        }
        for (int i = 0; i < request.getData().size(); i ++) {
            String sheetName = request.getData().get(i).get(splitIndex).toString();
            sheetNames.add(sheetName);
        }
        Iterator it = sheetNames.iterator();
        while(it.hasNext()) {
            String currentSheetName = it.next().toString();
            ExcelDataSheet sheet = new ExcelDataSheet();
            List<List<Object>> sheetData = new ArrayList<List<Object>>();
            for (int i = 0; i < request.getData().size(); i ++) {
                List<Object> nThItem = request.getData().get(i);
                List<Object> sheetSingleRow = new ArrayList<Object>();
                if (nThItem.contains(currentSheetName)) {
                    for(int j = 0; j < nThItem.size(); j ++) {
                        sheetSingleRow.add(nThItem.get(j));

                    }
                    sheetData.add(sheetSingleRow);
                }
            }
            sheet.setTitle(currentSheetName);
            sheet.setRows(sheetData);
            sheet.setHeaders(headers);
            sheets.add(sheet);
        }

        excelData.setSheets(sheets);
        excelData.setGeneratedTime(LocalDateTime.now());

        excelData.createName();

        ExcelFile excelFile=new ExcelFile();
        excelFile.setFileName(excelData.getName());
        excelFile.setSubmitter(request.getSubmitter());

        ExcelGenerationService excelGenerationServiceImpl=new ExcelGenerationServiceImpl();
        excelGenerationServiceImpl.generateExcelReport(excelData);

        return excelFile;
    }


    @Override
    public InputStream getExcelBodyById(String id) {
        Optional<ExcelFile> fileInfo = excelRepository.getFileById(id);
        String name=id+".xlsx";
        if (fileInfo.isPresent()) {
            File file = new File(name);
            try {// Exception handling
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

