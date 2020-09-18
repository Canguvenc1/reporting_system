package com.antra.evaluation.reporting_system.pojo.api;

import java.util.List;

public class ExcelRequest {
    private String submitter;
    private List<List<Object>> data;
    private List<String> headers;
    private String description;

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    //Printing class information to increase the code quality by overriding the toString() method, so that we can provide meaningful output.
    @Override
    public String toString() {
        return " Submitter: " + submitter + " Data " + data + " Headers " + headers + " Description: " + description ;
    }
}
