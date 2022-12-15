package com.best.electronics.controller.report;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.report.generator.GenerateCSVReport;
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
@RequestMapping("/report/csv")
public class CSVReportController {

    @GetMapping("/user")
    public String sendUserCSVReport(@RequestParam String fileName, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null){
            return "adminLogin";
        }else{
            String emailAddress = (String) oldSession.getAttribute("emailAddress");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateCSVReport());
            if(reportGeneratorService.getDataAndGenerateReport(databasePersistence, "{call get_all_user_details()}", fileName)){
                SendReportDelegator sendReportDelegator = new SendReportDelegator();
                ISendReport sendReport = sendReportDelegator.identifySender("SMTP");
                String fileNameWithExtension = fileName + ".csv";
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
    public String sendProductCSVReport(@RequestParam String fileName, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null){
            return "adminLogin";
        }else{
            String emailAddress = (String) oldSession.getAttribute("emailAddress");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateCSVReport());
            if(reportGeneratorService.getDataAndGenerateReport(databasePersistence, "{call get_product_list()}", fileName)){
                SendReportDelegator sendReportDelegator = new SendReportDelegator();
                ISendReport sendReport = sendReportDelegator.identifySender("SMTP");
                String fileNameWithExtension = fileName + ".csv";
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
    public String sendProductSoldCSVReport(@RequestParam String fileName, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null){
            return "adminLogin";
        }else{
            String emailAddress = (String) oldSession.getAttribute("emailAddress");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ReportGeneratorService reportGeneratorService = new ReportGeneratorService();
            reportGeneratorService.setReportGenerator(new GenerateCSVReport());
            if(reportGeneratorService.getDataAndGenerateReport(databasePersistence, "{call get_products_sold_details()}", fileName)){
                SendReportDelegator sendReportDelegator = new SendReportDelegator();
                ISendReport sendReport = sendReportDelegator.identifySender("SMTP");
                String fileNameWithExtension = fileName + ".csv";
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
