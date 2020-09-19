package com.antra.evaluation.reporting_system;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.*;
import com.antra.evaluation.reporting_system.service.ExcelGenerationService;
import com.antra.evaluation.reporting_system.service.ExcelService;
import com.antra.evaluation.reporting_system.service.ExcelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReportingSystemApplicationTests {

    @Autowired
    ExcelGenerationService reportService;
    ExcelRequest request;
    MultiSheetExcelRequest multiRequest;
    ExcelService excelService;
    ExcelData data = new ExcelData();
    ExcelResponse secondResponse;

    @BeforeEach // We are using JUnit 5, @Before is replaced by @BeforeEach
    public void setUpData() {
        request=new ExcelRequest();
        secondResponse=new ExcelResponse();
        multiRequest=new MultiSheetExcelRequest();
        excelService=new ExcelServiceImpl();
        data.setTitle("Test book");
        data.setGeneratedTime(LocalDateTime.now());

        var sheets = new ArrayList<ExcelDataSheet>();
        var sheet1 = new ExcelDataSheet();
        sheet1.setTitle("First Sheet");

        var headersS1 = new ArrayList<ExcelDataHeader>();
        ExcelDataHeader header1 = new ExcelDataHeader();
        header1.setName("NameTest");
        //       header1.setWidth(10000);
        header1.setType(ExcelDataType.STRING);
        headersS1.add(header1);

        ExcelDataHeader header2 = new ExcelDataHeader();
        header2.setName("Age");
        //   header2.setWidth(10000);
        header2.setType(ExcelDataType.NUMBER);
        headersS1.add(header2);

        List<List<Object>> dataRows = new ArrayList<>();
        List<Object> row1 = new ArrayList<>();
        row1.add("Dawei");
        row1.add(12);
        List<Object> row2 = new ArrayList<>();
        row2.add("Dawei2");
        row2.add(23);
        dataRows.add(row1);
        dataRows.add(row2);

        sheet1.setRows(dataRows);
        sheet1.setHeaders(headersS1);
        sheets.add(sheet1);
        data.setSheets(sheets);

        var sheet2 = new ExcelDataSheet();
        sheet2.setTitle("second Sheet");
        sheet2.setRows(dataRows);
        sheet2.setHeaders(headersS1);
        sheets.add(sheet2);
        ArrayList<String> myHeaders=new ArrayList<>();
        myHeaders.add("FirstColumn");
        myHeaders.add("SecondColumn");
        request.setHeaders(myHeaders);
        request.setSubmitter("Person");
        request.setDescription("Sheet1");
        request.setData(dataRows);

        multiRequest.setHeaders(myHeaders);
        multiRequest.setSubmitter("Person");
        multiRequest.setDescription("Sheet1");
        multiRequest.setData(dataRows);
        multiRequest.setSplitBy("SecondColumn");

    }

    @Test
    public void testExcelGegeration() {
        File file = null;
        try {
            file = reportService.generateExcelReport(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(file != null);
    }

    @Test
    public void testExcelList() throws IOException {
        ArrayList<ExcelResponse> myResponse =new ArrayList<>();
        ExcelFile excelFile=excelService.create(request);
        excelService.saveToRepo(excelFile);
        try{
            myResponse= (ArrayList<ExcelResponse>) excelService.showRepo();
        }catch (Exception e){
            e.printStackTrace();
        }
        assertTrue(myResponse.get(0) !=null);
    }

    @Test
    public void testExcelSingleCreate(){
        ExcelFile file = null;
        try{
            file=excelService.create(request);

        }catch (Exception e){
            e.printStackTrace();
        }
        assertTrue(file.getFileName() != null);

    }

    @Test
    public void testExcelMultiSheetCreate(){
        ExcelFile file = null;
        try{
            file=excelService.multiCreate(multiRequest);

        }catch (Exception e){
            e.printStackTrace();
        }
        assertTrue(file!= null);
    }

    @Test
    public void testExcelFileDelete() throws IOException {

        ExcelFile file =excelService.create(request);
        secondResponse.setFileId("same");
        secondResponse.setExcelFile(file);
        String fileName="same";
        String sec="same";
        try{
            secondResponse =excelService.deleteRepo(file.getFileName());
            fileName=secondResponse.getFileId();
        }catch (Exception e){
            e.printStackTrace();
        }if(secondResponse!=null){
            assertTrue(!Objects.equals(fileName, sec));
        }

    }


}
