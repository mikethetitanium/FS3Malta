package com.FS3Malta.FS3Malta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.FS3Malta.FS3Malta.Dto.WeeklyRecord;
import com.FS3Malta.FS3Malta.Dto.SocialSecurityResult;
import com.FS3Malta.FS3Malta.Repository.EmployeeRepository;
import com.FS3Malta.FS3Malta.Repository.WeeklyWageRepository;
import com.FS3Malta.FS3Malta.Dto.FS3Report;
import com.FS3Malta.FS3Malta.model.Employee;
import com.FS3Malta.FS3Malta.model.WeeklyWage;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class FS3ReportService {
@Autowired
private SocialSecurityCalculator socialSecurityCalculator;

@Autowired
private EmployeeRepository employeeRepository;

@Autowired
private WeeklyWageRepository weeklyWageRepository;

//generate a report for an employee for a certain year
public FS3Report generateReport(Long employeeId,int taxYear){
Employee employee=employeeRepository.findById(employeeId).orElseThrow(()->new RuntimeException("Employee not found"));

//Fetch wages for the specified year
List<WeeklyWage> weeklyWages=weeklyWageRepository.findByEmployeeIdAndYear(employeeId, taxYear);
if(weeklyWages.isEmpty()){
    throw new IllegalArgumentException("No wage records found for this employee for the year"+taxYear);
}
List<WeeklyRecord> weeklyRecords = weeklyWages.stream()
                .map(ww -> new WeeklyRecord(ww.getWageMonth(), ww.getWageWeek(), ww.getWageAmount()))
                .collect(Collectors.toList());
SocialSecurityResult socialSecurityResult=socialSecurityCalculator.calculateSocialSecurity(weeklyRecords);

// Derive basic weekly wage
        BigDecimal basicWeeklyWage = socialSecurityResult.getWeeksContributed() > 0
                ? socialSecurityResult.getTotalWages().divide(BigDecimal.valueOf(socialSecurityResult.getWeeksContributed()), 2, java.math.RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        // Calculate total gross emoluments from weekly wages
        BigDecimal totalGrossEmoluments = socialSecurityResult.getTotalWages();

        // Calculate tax deductions (approx 18.75% of gross for Malta)
        BigDecimal taxDeductionsMain = totalGrossEmoluments.multiply(new BigDecimal("0.1875"))
                .setScale(2, java.math.RoundingMode.HALF_UP);

        // Calculate SSC amounts (Malta rates: Payee Approx:10.5%, Payer Approx:15.75%)
        BigDecimal socialSecurityPayeeAmount = totalGrossEmoluments.multiply(new BigDecimal("0.105"))
                .setScale(2, java.math.RoundingMode.HALF_UP);
        BigDecimal socialSecurityPayerAmount = totalGrossEmoluments.multiply(new BigDecimal("0.1575"))
                .setScale(2, java.math.RoundingMode.HALF_UP);
        BigDecimal totalSocialSecurity = socialSecurityPayeeAmount.add(socialSecurityPayerAmount);

        // Maternity fund (Payer contribution Approx:0.6%)
        BigDecimal maternityFundPayerAmount = totalGrossEmoluments.multiply(new BigDecimal("0.006"))
                .setScale(2, java.math.RoundingMode.HALF_UP);

        return FS3Report.builder()
                // Payee Information
                .surname(employee.getSurname())
                .firstName(employee.getFirstName())
                .houseNo("B2")
                .street("Cooperative Bank Street")
                .locality("Nairobi")
                .postcode("PO BOX 14 Athi River")
                .telephoneNumber("+254 71950685")
                .taxYear(taxYear)
                .payeeIdCard(employee.getIdCard())
                .socialSecurityNo(employee.getSocialSecurityNo())
                .spouseIdCard(null)

                // Period
                .periodFrom(LocalDate.of(taxYear, 1, 1))
                .periodTo(LocalDate.of(taxYear, 12, 31))

                // Gross Emoluments (derived from database)
                .grossEmoluments(totalGrossEmoluments)
                .overtimeAmount(BigDecimal.ZERO)
                .overtimeHours(0)
                .directorsFees(BigDecimal.ZERO)
                .grossEmolumentsPartTime(BigDecimal.ZERO)
                .fringeBenefitsTotal(BigDecimal.ZERO)
                .shareOptions15(BigDecimal.ZERO)
                .totalGrossEmoluments(totalGrossEmoluments)

                // Fringe Benefits Breakdown
                .fringeBenefitsCat1(BigDecimal.ZERO)
                .fringeBenefitsCat2(BigDecimal.ZERO)
                .fringeBenefitsCat3(BigDecimal.ZERO)
                .nonTaxableCarCashAllowance(BigDecimal.ZERO)

                // Tax Deductions (calculated from gross)
                .taxDeductionsMain(taxDeductionsMain)
                .taxDeductionsOvertime(BigDecimal.ZERO)
                .taxDeductionsPartTime(BigDecimal.ZERO)
                .taxArrearsDeductions(BigDecimal.ZERO)
                .taxShareOptions(BigDecimal.ZERO)
                .totalTaxDeductions(taxDeductionsMain)

                // Social Security (dynamically calculated from database)
                .basicWeeklyWage(basicWeeklyWage)
                .weeksContributed(socialSecurityResult.getWeeksContributed())
                .sscCategory(socialSecurityResult.getCategory())
                .sscPayeeAmount(socialSecurityPayeeAmount)
                .sscPayerAmount(socialSecurityPayerAmount)
                .totalSSC(totalSocialSecurity)
                .maternityFundPayerAmount(maternityFundPayerAmount)
                .unpaidLeaveFrom(null)
                .unpaidLeaveTo(null)
                .unpaidLeaveWeeks(0)
                .totalPayeeSSC(socialSecurityPayeeAmount)
                .voluntaryPensionContribution(BigDecimal.ZERO)

                // Payer Information
                .businessName("Mercans")
                .businessAddress("123 Business Park")
                .businessLocality("United Kingdom")
                .businessPostcode("SLM 3210")
                .businessTelephoneNumber("+1 000000000")
                .principalFullName("Azhar Javed")
                .principalPosition("HR Manager")
                .payerPENumber("PE123456")
                .reportGeneratedDate(LocalDate.now())

                .build();
    }

}
