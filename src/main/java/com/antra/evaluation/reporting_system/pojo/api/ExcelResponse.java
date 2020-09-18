package com.antra.evaluation.reporting_system.pojo.api;

import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;

public class ExcelResponse {
    private String fileId;
    private ExcelFile excelFile;

    public ExcelFile getExcelFile() {
        return excelFile;
    }
    public void setExcelFile(ExcelFile excelFile) {
        this.excelFile = excelFile;
    }
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    //Printing class information to increase the code quality by overriding the toString() method, so that we can provide meaningful output.
    @Override
    public String toString() {
        return " File Unique Id: " + fileId + " Excel File: " + excelFile;
    }
}
