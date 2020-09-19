package com.antra.evaluation.reporting_system.endpoint;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import com.antra.evaluation.reporting_system.service.ExcelService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ExcelGenerationController {

    private static final Logger log = LoggerFactory.getLogger(ExcelGenerationController.class);

    ExcelService excelService;
    LogError logError;


    @Autowired
    public ExcelGenerationController(ExcelService excelService) {
        this.excelService = excelService;
        this.logError=new LogError();
    }

    /**
     *
     * @param request
     * @throws IOException
     * Generates Excel Files.
     */
    @PostMapping("/excel")
    @ApiOperation("Generate Excel")
    public ResponseEntity<ExcelResponse> createExcel(@RequestBody @Validated ExcelRequest request) throws IOException {
        ExcelResponse response = new ExcelResponse();
        if(!logError.checkRequest(request).equals("")){
            log.error(logError.checkRequest(request));// Log
        }if(request.getHeaders()!=null && request.getData()!=null){
            ExcelFile excelFile=excelService.create(request);
            excelService.saveToRepo(excelFile);
            log.info("File created");// Log
            response.setFileId(excelFile.getFileName());
            response.setExcelFile(excelFile);
        }



        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *
     * @param request
     * @throws IOException
     * Generates Multi-Sheet Excel files using splitBy.
     */
    @PostMapping("/excel/auto")
    @ApiOperation("Generate Multi-Sheet Excel Using Split field")
    public ResponseEntity<ExcelResponse> createMultiSheetExcel(@RequestBody @Validated MultiSheetExcelRequest request) throws IOException {
        ExcelResponse response = new ExcelResponse();

        if(!logError.checkMultiRequest(request).equals("")){
            log.error(logError.checkMultiRequest(request));// Log
        }if(request.getHeaders()!=null && request.getData()!=null && request.getSplitBy()!=null){
            ExcelFile excelFile= excelService.multiCreate(request);
            excelService.saveToRepo(excelFile);
            log.info("File created");// Log
            response.setFileId(excelFile.getFileName());
            response.setExcelFile(excelFile);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     *
     * Lists the existing all Excel Files.
     *
     */
    @GetMapping("/excel")
    @ApiOperation("List all existing files")
    public ResponseEntity<List<ExcelResponse>> listExcels() {
        var response = new ArrayList<ExcelResponse>();
        response= (ArrayList<ExcelResponse>) excelService.showRepo();
        if(response.isEmpty()){
            log.info("Empty repository");// Log
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/excel/{id}/content")
    public void downloadExcel(@PathVariable String id, HttpServletResponse response) throws IOException {
        String name=id+".xlsx";

        InputStream fis;
        try{// Exception handling
            fis = excelService.getExcelBodyById(id);
            response.setHeader("Content-Type","application/vnd.ms-excel");
            response.setHeader("Content-Disposition","attachment; filename="+name); // TODO: File name cannot be hardcoded here
            FileCopyUtils.copy(fis, response.getOutputStream());
        }catch (Exception exception){
            log.error("Problem occured in input stream, no input stream");
        }

    }

    /**
     *
     * @param id
     * Excel files deletion operation.
     */
    @DeleteMapping("/excel/{id}")
    public ResponseEntity<ExcelResponse> deleteExcel(@PathVariable String id) {
        var response = new ExcelResponse();
        response=excelService.deleteRepo(id);
        if(response==null){
            log.info("File doesn't exist");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
// Log
// Exception handling
// Validation
