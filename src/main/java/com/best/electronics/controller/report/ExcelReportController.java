package com.best.electronics.controller.report;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.report.generator.GenerateExcelReport;
import com.best.electronics.report.generator.ReportGeneratorService;
import com.best.electronics.report.sender.ISendReport;
import com.best.electronics.report.sender.SendReportDelegator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/report/excel")
public class ExcelReportController {

    @GetMapping("/user")
    public String sendUserExcelReport(@RequestParam String fileName, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null){
            return "adminLogin";
        }else{
            String emailAddress = (String) oldSession.getAttribute("emailAddress");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateExcelReport());
            if(reportGeneratorService.getDataAndGenerateReport(databasePersistence, "{call get_all_user_details()}", fileName)){
                SendReportDelegator sendReportDelegator = new SendReportDelegator();
                ISendReport sendReport = sendReportDelegator.identifySender("SMTP");
                String fileNameWithExtension = fileName + ".xlsx";
                if(sendReport.sendReport(emailAddress, fileNameWithExtension)){
                    oldSession.setAttribute("msg", "Report is successfully sent!");
                    return "redirect:/reports";
                }
            }
            oldSession.setAttribute("msg", "Some error occurred while sending Report! Please try again!");
            return "redirect:/reports";
        }
    }

    @GetMapping("/products")
    public String sendProductExcelReport(@RequestParam String fileName, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null){
            return "adminLogin";
        }else{
            String emailAddress = (String) oldSession.getAttribute("emailAddress");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateExcelReport());
            if(reportGeneratorService.getDataAndGenerateReport(databasePersistence, "{call get_product_list()}", fileName)){
                SendReportDelegator sendReportDelegator = new SendReportDelegator();
                ISendReport sendReport = sendReportDelegator.identifySender("SMTP");
                String fileNameWithExtension = fileName + ".xlsx";
                if(sendReport.sendReport(emailAddress, fileNameWithExtension)){
                    oldSession.setAttribute("msg", "Report is successfully sent!");
                    return "redirect:/reports";
                }
            }
            oldSession.setAttribute("msg", "Some error occurred while sending Report! Please try again!");
            return "redirect:/reports";
        }
    }

    @GetMapping("/products_sold")
    public String sendProductSoldExcelReport(@RequestParam String fileName, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null){
            return "adminLogin";
        }else{
            String emailAddress = (String) oldSession.getAttribute("adminEmailAddress");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateExcelReport());
            if(reportGeneratorService.getDataAndGenerateReport(databasePersistence, "{call get_products_sold_details()}", fileName)){
                SendReportDelegator sendReportDelegator = new SendReportDelegator();
                ISendReport sendReport = sendReportDelegator.identifySender("SMTP");
                String fileNameWithExtension = fileName + ".xlsx";
                if(sendReport.sendReport(emailAddress, fileNameWithExtension)){
                    oldSession.setAttribute("msg", "Report is successfully sent!");
                    return "redirect:/reports";
                }
            }
            oldSession.setAttribute("msg", "Some error occurred while sending Report! Please try again!");
            return "redirect:/reports";
        }
    }
}
