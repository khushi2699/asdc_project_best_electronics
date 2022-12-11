package com.best.electronics.controller.report;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ReportController {

    @GetMapping("/reports")
    public String sendUserExcelReport(HttpServletRequest request, Model model){
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            String message = (String) oldSession.getAttribute("msg");
            System.out.println(message);
            model.addAttribute("msg", message);
        }
        return "reportOptions";
    }

    @PostMapping("/process_reports")
    public String processReport(HttpServletRequest request){
        String format = request.getParameter("format");
        String category = request.getParameter("category");
        String fileName = request.getParameter("fileName");
        System.out.println(format);
        return "redirect:/report/" + format + "/" + category + "?fileName=" + fileName;
    }
}
