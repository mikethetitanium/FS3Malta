package com.FS3Malta.FS3Malta.service;

import com.FS3Malta.FS3Malta.Dto.FS3Report;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class FS3PDFGenerator {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final BaseColor SECTION_BLUE = new BaseColor(74, 144, 164);

    public byte[] generatePDF(FS3Report report) throws DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        PdfWriter.getInstance(document, baos);
        document.open();

        // Add banner image
        try {
            String bannerPath = "src/main/resources/static/banner.png";
            Image bannerImage = Image.getInstance(bannerPath);
            bannerImage.setWidthPercentage(100);
            bannerImage.scaleToFit(PageSize.A4.getWidth() - 40, 80);
            document.add(bannerImage);
            document.add(new Paragraph(" "));
        } catch (Exception e) {
            // If banner image not found, continue without it
        }

        // SECTION A: PAYEE INFORMATION
        document.add(createSectionHeader("A", "PAYEE INFORMATION"));
        PdfPTable payeeTable = createFormTable(4);
        addFormRow(payeeTable, "Surname", report.getSurname(), "For Year Ended 31 December", report.getTaxYear().toString());
        addFormRow(payeeTable, "First Name", report.getFirstName(), "Payee's ID Card / IT Reg No.", report.getPayeeIdCard());
        addFormRow(payeeTable, "Address (House No.)", report.getHouseNo(), "Payee's Social Security No.", report.getSocialSecurityNo());
        addFormRow(payeeTable, "Street", report.getStreet(), "Spouse's ID Card / IT Reg No.", report.getSpouseIdCard() != null ? report.getSpouseIdCard() : "");
        addFormRow(payeeTable, "Locality", report.getLocality(), "", "");
        addFormRow(payeeTable, "Postcode", report.getPostcode(), "", "");
        addFormRow(payeeTable, "Telephone Number", report.getTelephoneNumber(), "", "");
        document.add(payeeTable);
        document.add(new Paragraph(" "));

        // SECTION B: PERIOD
        document.add(createSectionHeader("B", "PERIOD"));
        PdfPTable periodTable = createFormTable(4);
        addFormRow(periodTable, "From", report.getPeriodFrom().format(dateFormatter), "To", report.getPeriodTo().format(dateFormatter));
        document.add(periodTable);
        document.add(new Paragraph(" "));

        // SECTION C: GROSS EMOLUMENTS
        document.add(createSectionHeader("C", "GROSS EMOLUMENTS"));
        PdfPTable emolTable = createFormTable(4);
        addFormRow(emolTable, "Gross Emoluments (FSS Main/Other)", formatCurrency(report.getGrossEmoluments()), "Number of Overtime Hours", report.getOvertimeHours().toString());
        addFormRow(emolTable, "Overtime (Eligible for 15% tax)", formatCurrency(report.getOvertimeAmount()), "Director's Fees", formatCurrency(report.getDirectorsFees()));
        addFormRow(emolTable, "Gross Emoluments (Part-time method)", formatCurrency(report.getGrossEmolumentsPartTime()), "Fringe Benefits (Total)", formatCurrency(report.getFringeBenefitsTotal()));
        addFormRow(emolTable, "Share Options (15%)", formatCurrency(report.getShareOptions15()), "Total Gross Emoluments", formatCurrency(report.getTotalGrossEmoluments()));
        addFormRow(emolTable, "Cat 1 (Car)", formatCurrency(report.getFringeBenefitsCat1()), "Cat 2 (Use of Assets)", formatCurrency(report.getFringeBenefitsCat2()));
        addFormRow(emolTable, "Cat 3 (Other)", formatCurrency(report.getFringeBenefitsCat3()), "Non Taxable Car Cash Allowance", formatCurrency(report.getNonTaxableCarCashAllowance()));
        document.add(emolTable);
        document.add(new Paragraph(" "));

        // SECTION D: TOTAL DEDUCTIONS
        document.add(createSectionHeader("D", "TOTAL DEDUCTIONS"));
        PdfPTable taxTable = createFormTable(4);
        addFormRow(taxTable, "Tax Deductions (FSS Main)", formatCurrency(report.getTaxDeductionsMain()), "Tax Deductions (Eligible Overtime)", formatCurrency(report.getTaxDeductionsOvertime()));
        addFormRow(taxTable, "Tax Deductions (Part-time)", formatCurrency(report.getTaxDeductionsPartTime()), "Tax Arrears Deductions", formatCurrency(report.getTaxArrearsDeductions()));
        addFormRow(taxTable, "15% Tax on Share Options", formatCurrency(report.getTaxShareOptions()), "Total Tax Deductions", formatCurrency(report.getTotalTaxDeductions()));
        document.add(taxTable);
        document.add(new Paragraph(" "));

        // SECTION E: SOCIAL SECURITY AND MATERNITY FUND
        document.add(createSectionHeader("E", "SOCIAL SECURITY AND MATERNITY FUND INFORMATION"));
        PdfPTable sscTable = createFormTable(4);
        addFormRow(sscTable, "Basic Weekly Wage (€)", formatCurrency(report.getBasicWeeklyWage()), "Number of Weeks", report.getWeeksContributed().toString());
        addFormRow(sscTable, "SSC Category", report.getSscCategory(), "Social Security Contributions (Payee)", formatCurrency(report.getSscPayeeAmount()));
        addFormRow(sscTable, "Social Security Contributions (Payer)", formatCurrency(report.getSscPayerAmount()), "Total SSC", formatCurrency(report.getTotalSSC()));
        addFormRow(sscTable, "Maternity Fund Contributions (Payer)", formatCurrency(report.getMaternityFundPayerAmount()), "Weeks Without Pay (From)", report.getUnpaidLeaveFrom() != null ? report.getUnpaidLeaveFrom().format(dateFormatter) : "");
        addFormRow(sscTable, "Weeks Without Pay (To)", report.getUnpaidLeaveTo() != null ? report.getUnpaidLeaveTo().format(dateFormatter) : "", "Weeks Without Pay (Number)", report.getUnpaidLeaveWeeks().toString());
        addFormRow(sscTable, "Total Payee SSC", formatCurrency(report.getTotalPayeeSSC()), "Voluntary Occupational Pension Scheme", formatCurrency(report.getVoluntaryPensionContribution()));
        document.add(sscTable);
        document.add(new Paragraph(" "));

        // SECTION F: PAYER INFORMATION
        document.add(createSectionHeader("F", "PAYER INFORMATION"));
        PdfPTable payerTable = createFormTable(4);
        addFormRow(payerTable, "Business Name", report.getBusinessName(), "Payer PE Number", report.getPayerPENumber());
        addFormRow(payerTable, "Business Address", report.getBusinessAddress(), "Date", report.getReportGeneratedDate().format(dateFormatter));
        addFormRow(payerTable, "Locality", report.getBusinessLocality(), "", "");
        addFormRow(payerTable, "Postcode", report.getBusinessPostcode(), "", "");
        addFormRow(payerTable, "Telephone Number", report.getBusinessTelephoneNumber(), "", "");
        addFormRow(payerTable, "Principal's Full Name", report.getPrincipalFullName(), "", "");
        addFormRow(payerTable, "Principal's Position", report.getPrincipalPosition(), "", "");
        document.add(payerTable);

        document.close();
        return baos.toByteArray();
    }

    private Paragraph createSectionHeader(String letter, String title) throws DocumentException {
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        try {
            headerTable.setTotalWidth(new float[]{30, 570});
        } catch (DocumentException e) {
            // Continue without setting total width
        }
        
        PdfPCell letterCell = new PdfPCell(new Paragraph(letter, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        letterCell.setBackgroundColor(SECTION_BLUE);
        letterCell.setPadding(5);
        letterCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell titleCell = new PdfPCell(new Paragraph(title, new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE)));
        titleCell.setBackgroundColor(SECTION_BLUE);
        titleCell.setPadding(5);
        
        headerTable.addCell(letterCell);
        headerTable.addCell(titleCell);
        
        Paragraph p = new Paragraph();
        p.add(headerTable);
        p.setSpacingAfter(5);
        return p;
    }

    private PdfPTable createFormTable(int columns) {
        PdfPTable table = new PdfPTable(columns);
        table.setWidthPercentage(100);
        table.setSpacingBefore(0);
        table.setSpacingAfter(0);
        return table;
    }

    private void addFormRow(PdfPTable table, String label1, String value1, String label2, String value2) {
        // Label 1
        PdfPCell labelCell1 = new PdfPCell(new Paragraph(label1, new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD)));
        labelCell1.setBackgroundColor(new BaseColor(240, 240, 240));
        labelCell1.setPadding(4);
        labelCell1.setBorderWidth(0.5f);
        
        // Value 1
        PdfPCell valueCell1 = new PdfPCell(new Paragraph(value1 != null ? value1 : "", new Font(Font.FontFamily.HELVETICA, 9)));
        valueCell1.setPadding(4);
        valueCell1.setBorderWidth(0.5f);
        
        // Label 2
        PdfPCell labelCell2 = new PdfPCell(new Paragraph(label2, new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD)));
        labelCell2.setBackgroundColor(new BaseColor(240, 240, 240));
        labelCell2.setPadding(4);
        labelCell2.setBorderWidth(0.5f);
        
        // Value 2
        PdfPCell valueCell2 = new PdfPCell(new Paragraph(value2 != null ? value2 : "", new Font(Font.FontFamily.HELVETICA, 9)));
        valueCell2.setPadding(4);
        valueCell2.setBorderWidth(0.5f);
        
        table.addCell(labelCell1);
        table.addCell(valueCell1);
        table.addCell(labelCell2);
        table.addCell(valueCell2);
    }

    private String formatCurrency(java.math.BigDecimal amount) {
        if (amount == null) return "€0.00";
        return "€" + String.format("%.2f", amount);
    }
}
