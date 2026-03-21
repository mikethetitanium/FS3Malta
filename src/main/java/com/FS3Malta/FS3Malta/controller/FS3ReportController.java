package com.FS3Malta.FS3Malta.controller;

import com.FS3Malta.FS3Malta.model.Employee;
import com.FS3Malta.FS3Malta.Dto.FS3Report;
import com.FS3Malta.FS3Malta.Repository.EmployeeRepository;
import com.FS3Malta.FS3Malta.service.FS3ReportService;
import com.FS3Malta.FS3Malta.service.FS3PDFGenerator;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FS3ReportController {

    @Autowired
    private FS3ReportService reportService;

    @Autowired
    private FS3PDFGenerator pdfGenerator;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/reports/fs3";
    }

    @GetMapping("/reports/fs3")
    public String showFS3Form(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        model.addAttribute("years", new int[]{2022,2023, 2024,2025});
        return "fs3-form";
    }

    @PostMapping("/reports/fs3/generate")
    public ResponseEntity<byte[]> generateFS3ReportPDF(
            @RequestParam Long employeeId,
            @RequestParam int year) {
        try {
            FS3Report report = reportService.generateReport(employeeId, year);
            byte[] pdfContent = pdfGenerator.generatePDF(report);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", 
                    "FS3_" + report.getPayeeIdCard() + "_" + year + ".pdf");

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (DocumentException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reports/fs3/view/{employeeId}/{year}")
    public String viewFS3Report(
            @PathVariable Long employeeId,
            @PathVariable int year,
            Model model) {
        FS3Report report = reportService.generateReport(employeeId, year);
        model.addAttribute("report", report);
        return "fs3-report";
    }
}
