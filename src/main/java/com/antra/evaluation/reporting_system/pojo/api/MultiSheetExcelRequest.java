package com.antra.evaluation.reporting_system.pojo.api;

public class MultiSheetExcelRequest extends ExcelRequest{
    private String splitBy;
    public String getSplitBy() {
        return this.splitBy;
    }
    public void setSplitBy(String splitBy) {
        this.splitBy = splitBy;
    }


    //Printing class information to increase the code quality by overriding the toString() method, so that we can provide meaningful output.
    @Override
    public String toString() {
        return " splitBy: "+ splitBy;
    }
}
