package com.antra.evaluation.reporting_system.pojo.report;

import java.time.LocalDateTime;
import java.util.List;

public class ExcelData {
    private String title;
    private LocalDateTime generatedTime;
    private List<ExcelDataSheet> sheets;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedTime(LocalDateTime generatedTime) {
        this.generatedTime = generatedTime;
    }

    public List<ExcelDataSheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<ExcelDataSheet> sheets) {
        this.sheets = sheets;
    }

    //Printing class information to increase the code quality by overriding the toString() method, so that we can provide meaningful output.
    @Override
    public String toString() {
        return " Title data: " + title + " Generated Time data " + generatedTime + " Sheets: " + sheets;
    }
}
