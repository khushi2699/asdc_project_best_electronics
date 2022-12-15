package com.best.electronics.report.generator;

import com.best.electronics.properties.ReportProperties;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenerateCSVReport implements IReportGenerator{

    @Override
    public Boolean generateReport(ArrayList<Map<String, Object>> data, String fileName) {
        try{
            List<ArrayList<String>> csvData = convertDataToCSVFormat(data);
            ReportProperties reportProperties = new ReportProperties();
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(reportProperties.getFileLocation() + fileName + ".csv"));
            for (ArrayList<String> record : csvData) {
                writer.write(String.join(",", record));
                writer.newLine();
            }
            writer.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private List<ArrayList<String>> convertDataToCSVFormat(ArrayList<Map<String, Object>> data) {
        List<ArrayList<String>> modifyData = new ArrayList<>();

        Map<String, Object> firstEntry = data.get(0);
        ArrayList<String> firstEntryList = new ArrayList<>(firstEntry.keySet());
        ArrayList<String> headerList = new ArrayList<>();
        for (String key : firstEntryList) {
            headerList.add(String.valueOf(key));
        }
        modifyData.add(headerList);

        for(Map<String, Object> map : data) {
            ArrayList<String> list = new ArrayList<>();
            for (Map.Entry<String, Object> mapEntry : map.entrySet()) {
                list.add(String.valueOf(mapEntry.getValue()).replaceAll(",", "|"));
            }
            modifyData.add(list);
        }
        return modifyData;
    }

}
