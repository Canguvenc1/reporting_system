package com.antra.evaluation.reporting_system.pojo.report;

public class ExcelFile {
    private String submitter;
    private String fileName;
    private ExcelData excelData;

    public ExcelData getExcelData() {
        return excelData;
    }

    public void setExcelData(ExcelData excelData) {
        this.excelData = excelData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    //Printing class information to increase the code quality by overriding the toString() method, so that we can provide meaningful output.
    @Override
    public String toString() {
        return " Submitter : " + submitter + " File Name: " + fileName + " Excel Data " + excelData;
    }
}
