package com.best.electronics.report.generator;

import java.util.ArrayList;

import java.util.Map;

public interface IReportGenerator {
    Boolean generateReport(ArrayList<Map<String, Object>> data, String fileName);
}
