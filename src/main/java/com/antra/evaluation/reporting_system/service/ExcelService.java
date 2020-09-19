package com.antra.evaluation.reporting_system.service;

import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import com.antra.evaluation.reporting_system.pojo.api.MultiSheetExcelRequest;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public interface ExcelService {
    InputStream getExcelBodyById(String id);
    boolean saveToRepo(ExcelFile file);
    ExcelFile create(ExcelRequest request) throws IOException;
    List<ExcelResponse> showRepo();
    ExcelResponse deleteRepo(String id);
    ExcelFile multiCreate(MultiSheetExcelRequest request) throws IOException;
}
