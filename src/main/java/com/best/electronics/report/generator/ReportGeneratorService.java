package com.best.electronics.report.generator;

import com.best.electronics.database.IDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;

public class ReportGeneratorService {
    private IReportGenerator reportGenerator;

    public void setReportGenerator(IReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    public Boolean getDataAndGenerateReport(IDatabasePersistence databasePersistence, String query, String fileName){
        ArrayList<Map<String, Object>> data = getDataFromDatabase(databasePersistence, query);
        if(data == null){
            return false;
        }else{
            return reportGenerator.generateReport(data, fileName);
        }
    }

    public ArrayList<Map<String, Object>> getDataFromDatabase(IDatabasePersistence databasePersistence, String query){
        try{
            return databasePersistence.loadData(query,  new ArrayList<>());
        }catch(Exception e){
            return null;
        }
    }

}
