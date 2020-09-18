package com.antra.evaluation.reporting_system.pojo.report;

public class ExcelDataHeader {
    private String name;
    private ExcelDataType type;
    private int width;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExcelDataType getType() {
        return type;
    }

    public void setType(ExcelDataType type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    //Printing class information to increase the code quality by overriding the toString() method, so that we can provide meaningful output.
    @Override
    public String toString() {
        return " Name data: " + name + " Type data: " + type + " Width data: " + width ;
    }
}
