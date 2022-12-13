package com.best.electronics.report;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.UserMockDatabasePersistence;
import com.best.electronics.properties.ReportProperties;
import com.best.electronics.report.generator.GenerateCSVReport;
import com.best.electronics.report.generator.GenerateExcelReport;
import com.best.electronics.report.generator.ReportGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ReportGeneratorServiceTest {

    IDatabasePersistence mockDatabasePersistence;

    @BeforeEach
    public void init(){
        mockDatabasePersistence = new UserMockDatabasePersistence();
    }

    @Test
    public void generateCSVReportSuccess(){
        try (MockedConstruction<ReportProperties> mocked = Mockito.mockConstruction(ReportProperties.class,
                (mock, context) -> when(mock.getFileLocation()).thenReturn("reports/test/"))) {
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateCSVReport());
            Boolean status = reportGeneratorService.getDataAndGenerateReport(mockDatabasePersistence, "{call get_all_user_details()}", "test");

            Assertions.assertEquals(true, status);
        }
    }

    @Test
    public void generateExcelReportSuccess(){
        try (MockedConstruction<ReportProperties> mocked = Mockito.mockConstruction(ReportProperties.class,
                (mock, context) -> {when(mock.getFileLocation()).thenReturn("reports/test/");
                                    when(mock.getExcelSheetName()).thenReturn("test");})) {
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateExcelReport());
            Boolean status = reportGeneratorService.getDataAndGenerateReport(mockDatabasePersistence, "{call get_all_user_details()}", "test");

            Assertions.assertEquals(true, status);
        }
    }

    @Test
    public void generateCSVReportFailWhenDBIssue(){
        try (MockedConstruction<ReportProperties> mocked = Mockito.mockConstruction(ReportProperties.class,
                (mock, context) -> when(mock.getFileLocation()).thenReturn("reports/test/"))) {
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateCSVReport());
            Boolean status = reportGeneratorService.getDataAndGenerateReport(mockDatabasePersistence, "{call get_all_products_details()}", "test");

            Assertions.assertEquals(false, status);
        }
    }

    @Test
    public void generateExcelReportFailWhenDBIssue(){
        try (MockedConstruction<ReportProperties> mocked = Mockito.mockConstruction(ReportProperties.class,
                (mock, context) -> {when(mock.getFileLocation()).thenReturn("reports/test/");
                                    when(mock.getExcelSheetName()).thenReturn("test");})) {
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateExcelReport());
            Boolean status = reportGeneratorService.getDataAndGenerateReport(mockDatabasePersistence, "{call get_all_products_details()}", "test");

            Assertions.assertEquals(false, status);
        }
    }

    @Test
    public void generateCSVReportFailWhenFolderIssue(){
        try (MockedConstruction<ReportProperties> mocked = Mockito.mockConstruction(ReportProperties.class,
                (mock, context) -> when(mock.getFileLocation()).thenReturn("reports/test/"))) {
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateCSVReport());
            Boolean status = reportGeneratorService.getDataAndGenerateReport(mockDatabasePersistence, "{call get_all_products_details()}", "test");

            Assertions.assertEquals(false, status);
        }
    }

    @Test
    public void generateExcelReportFailWhenFolderIssue(){
        try (MockedConstruction<ReportProperties> mocked = Mockito.mockConstruction(ReportProperties.class,
                (mock, context) -> {when(mock.getFileLocation()).thenReturn("reports/test1/");
                                    when(mock.getExcelSheetName()).thenReturn("test");})) {
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateExcelReport());
            Boolean status = reportGeneratorService.getDataAndGenerateReport(mockDatabasePersistence, "{call get_all_products_details()}", "test");

            Assertions.assertEquals(false, status);
        }
    }
}
