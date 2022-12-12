package com.best.electronics.report.generator;

import com.best.electronics.properties.ReportProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenerateExcelReport implements IReportGenerator{

    @Override
    public Boolean generateReport(ArrayList<Map<String, Object>> data, String fileName) {
        try{
            ReportProperties reportProperties = new ReportProperties();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(reportProperties.getExcelSheetName());

            writeHeaderLine(sheet, data.get(0));
            writeDataInFile(data, sheet);

            FileOutputStream outputStream = new FileOutputStream(reportProperties.getFileLocation() + fileName);
            workbook.write(outputStream);

            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void writeHeaderLine(XSSFSheet sheet, Map<String, Object> firstEntry) {
        Row headerRow = sheet.createRow(0);

        List<String> keyList = new ArrayList<>(firstEntry.keySet());
        Cell headerCell;
        for(int i=0; i<keyList.size(); i++){
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(keyList.get(i));
        }
    }

    private void writeDataInFile(ArrayList<Map<String, Object>> data, XSSFSheet sheet) {
        int rowCount = 1;

        for(Map<String, Object> d: data){
            Row row = sheet.createRow(rowCount++);

            List<String> keyList = new ArrayList<>(d.keySet());
            for (int i = 0; i < keyList.size(); i++) {
                Cell cell = row.createCell(i);
                String key = keyList.get(i);
                cell.setCellValue(String.valueOf(d.get(key)));
            }
        }
    }

}
