package com.FS3Malta.FS3Malta.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FS3Report {
    // Payee Information
    private String surname;
    private String firstName;
    private String houseNo;
    private String street;
    private String locality;
    private String postcode;
    private String telephoneNumber;
    private Integer taxYear;
    private String payeeIdCard;
    private String socialSecurityNo;
    private String spouseIdCard;

    // Period
    private LocalDate periodFrom;
    private LocalDate periodTo;

    // Gross Emoluments
    private BigDecimal grossEmoluments;
    private BigDecimal overtimeAmount;
    private Integer overtimeHours;
    private BigDecimal directorsFees;
    private BigDecimal grossEmolumentsPartTime;
    private BigDecimal fringeBenefitsTotal;
    private BigDecimal shareOptions15;
    private BigDecimal totalGrossEmoluments;

    // Fringe Benefits
    private BigDecimal fringeBenefitsCat1;
    private BigDecimal fringeBenefitsCat2;
    private BigDecimal fringeBenefitsCat3;
    private BigDecimal nonTaxableCarCashAllowance;

    // Tax Deductions
    private BigDecimal taxDeductionsMain;
    private BigDecimal taxDeductionsOvertime;
    private BigDecimal taxDeductionsPartTime;
    private BigDecimal taxArrearsDeductions;
    private BigDecimal taxShareOptions;
    private BigDecimal totalTaxDeductions;

    // Social Security
    private BigDecimal basicWeeklyWage;
    private Integer weeksContributed;
    private String sscCategory;
    private BigDecimal sscPayeeAmount;
    private BigDecimal sscPayerAmount;
    private BigDecimal totalSSC;
    private BigDecimal maternityFundPayerAmount;
    private LocalDate unpaidLeaveFrom;
    private LocalDate unpaidLeaveTo;
    private Integer unpaidLeaveWeeks;
    private BigDecimal totalPayeeSSC;
    private BigDecimal voluntaryPensionContribution;

    // Payer Information
    private String businessName;
    private String businessAddress;
    private String businessLocality;
    private String businessPostcode;
    private String businessTelephoneNumber;
    private String principalFullName;
    private String principalPosition;
    private String payerPENumber;
    private LocalDate reportGeneratedDate;
}
