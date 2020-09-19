package com.antra.evaluation.reporting_system;

import com.antra.evaluation.reporting_system.endpoint.ExcelGenerationController;
import com.antra.evaluation.reporting_system.pojo.api.ExcelRequest;
import com.antra.evaluation.reporting_system.pojo.api.ExcelResponse;
import com.antra.evaluation.reporting_system.pojo.report.ExcelData;
import com.antra.evaluation.reporting_system.pojo.report.ExcelFile;
import com.antra.evaluation.reporting_system.service.ExcelService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class APITest {
    @Mock
    ExcelService excelService;

    @BeforeEach
    public void configMock() {
        MockitoAnnotations.initMocks(this);
        RestAssuredMockMvc.standaloneSetup(new ExcelGenerationController(excelService));
    }

    @Test
    public void testFileDownload() throws FileNotFoundException {
        InputStream iStream = null;
        ExcelFile excelFile=new ExcelFile();
        ExcelData ed= new ExcelData();
        String fileName = String.valueOf(ed.getGeneratedTime());
        fileName=fileName.replace(":","-");
        fileName=fileName.replace(".","-");
        fileName=fileName.replace(" ","-");
        excelFile.setFileName(fileName);

        Mockito.when(excelService.getExcelBodyById(excelFile.getFileName())).thenReturn(iStream);
        given().accept("application/json").get("/excel/"+fileName+"/content").peek().
                then().assertThat()
                .statusCode(200);
    }
    @Test
    public void testDeleteFile() throws IOException {
        ExcelFile file = new ExcelFile();
        ExcelData ed= new ExcelData();
        String fileName = String.valueOf(ed.getGeneratedTime());
        fileName=fileName.replace(":","-");
        fileName=fileName.replace(".","-");
        fileName=fileName.replace(" ","-");
        file.setFileName(fileName);
        ExcelResponse response = new ExcelResponse();
        
        String id=file.getFileName();
        Mockito.when(excelService.deleteRepo(id)).thenReturn(response);
        given().delete("/excel/"+id).peek().
                then().assertThat()
                .statusCode(200);
    }

    @Test
    public void testListFiles() throws FileNotFoundException {
        ArrayList<ExcelResponse> myList=new ArrayList<>();
        Mockito.when(excelService.showRepo()).thenReturn(myList);
        given().accept("application/json").get("/excel").peek().
                then().assertThat()
                .statusCode(200);
    }
    @Test
    public void testExcelGeneration() throws IOException {
        ExcelFile myFile = new ExcelFile();
        Mockito.when(excelService.create(any())).thenReturn(myFile);
        given().accept("application/json").contentType(ContentType.JSON).body("{\"headers\":[\"Name\",\"Age\"], \"data\":[[\"Teresa\",\"5\"],[\"Daniel\",\"1\"]] ,\"submitter\":\"person\", \"description\":\"firstFile\"}").post("/excel").peek().
                then().assertThat()
                .statusCode(200);
    }
    @Test
    public void testMultiSheetExcelGeneration() throws IOException {
        ExcelFile myFile = new ExcelFile();
        Mockito.when(excelService.create(any())).thenReturn(myFile);
        given().accept("application/json").contentType(ContentType.JSON).body("{\"headers\":[\"Name\",\"Age\"], \"data\":[[\"Teresa\",\"5\"],[\"Daniel\",\"1\"]] ,\"submitter\":\"person\", \"description\":\"firstFile\", \"splitBy\":\"split\"}").post("/excel").peek().
                then().assertThat()
                .statusCode(200);
    }
    


}
