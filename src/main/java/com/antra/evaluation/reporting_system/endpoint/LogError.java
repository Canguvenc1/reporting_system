package com.antra.evaluation.reporting_system.endpoint;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;

public class LogError {
    private String error;

    public LogError( ){
        error="NoError";
    }

    //Checks the description information is involved in the request.
    public String checkRequest(ExcelRequest request){
        error="";
        if(request.getDescription()==null){
            error=error+ " Your request doesn't have description";
        }
        //Checks the data information is involved in the request.
        if(request.getData()==null){
            error= error+ " Your request doesn't have any data, file didn't created";
        }
        //Checks the header information is involved in the request.
        if(request.getHeaders()==null){
            error=error+ " Your request doesn't have any header, file didn't created";
        }
        //Checks the submitter information is involved in the request.
        if(request.getSubmitter()==null){
            error=error+ " Your request doesn't have a submitter information";
        }
        return error;
    }

    //Checks multi sheet Excel Files request informations.
    public String checkMultiRequest(MultiSheetExcelRequest request){
        error="";
        if(request.getDescription()==null){
            error=error+  " Your request doesn't have description";
        }
        //Checks the data information is involved in the request.
        if(request.getData()==null){
            error=error+ " Your request doesn't have any data file didn't created";
        }
        //Checks the header information is involved in the request.
        if(request.getHeaders()==null){
            error=error+ " Your request doesn't have any header file didn't created";
        }
        //Checks the submitter information is involved in the request.
        if(request.getSubmitter()==null){
            error=error+ " Your request doesn't have a submitter information";
        }
        if(request.getSplitBy()==null){
            error=error+ " Your request doesn't have information for split operation file didn't created";
        }
        return error;
    }
}
