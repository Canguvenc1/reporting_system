package com.antra.evaluation.reporting_system.repo;

import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ExcelRepositoryImpl implements ExcelRepository {

    Map<String, ExcelFile> excelData = new ConcurrentHashMap<>();


    /**
     *
     * @param id
     * @return ExcelFile
     * Gets the Excel Files by their id's.
     */
    @Override
    public Optional<ExcelFile> getFileById(String id) {
        return Optional.ofNullable(excelData.get(id));
    }

    /**
     *
     * @param file
     * @return ExcelFile
     * Saves the Excel Files which is generated.
     */
    @Override
    public ExcelFile saveFile(ExcelFile file) {
        excelData.put(file.getFileName(),file);
        return file;
    }

    /**
     *
     * @param id
     * @return ExcelFile
     * Removes the Excel Files permanently
     */

    @Override
    public ExcelFile deleteFile(String id) {
        ExcelFile file=excelData.get(id);
        excelData.remove(id);
        return file;
    }

    /**
     *
     * @return ExcelFile List
     * Helps to get the Excel Files
     */

    @Override
    public List<ExcelFile> getFiles() {
        List<ExcelFile> files=new ArrayList<>();
        for (Map.Entry<String,ExcelFile> entry : excelData.entrySet()){
            files.add(entry.getValue());
        }

        return files;
    }
}

