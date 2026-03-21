package com.FS3Malta.FS3Malta.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.FS3Malta.FS3Malta.Dto.SocialSecurityResult;
import com.FS3Malta.FS3Malta.Dto.WeeklyRecord;
import java.util.List;

@Service
public class SocialSecurityCalculator {

    public SocialSecurityResult calculateSocialSecurity(List<WeeklyRecord> weeklyRecords) {
       
        BigDecimal totalWages = BigDecimal.ZERO;
        int weeksContributed=0;
                
        for (WeeklyRecord weeklyRecord : weeklyRecords) {
            if(weeklyRecord.getWageAmount()!=null && weeklyRecord.getWageAmount().compareTo(BigDecimal.ZERO))>0){
                totalWages = totalWages.add(weeklyRecord.getWageAmount());
                weeksContributed++;
            }

        }

         // Determine contribution category based on payroll period
        String category = determineCategory(weeklyRecords);

        return new SocialSecurityResult(totalWages, weeksContributed, category);
    }

   
    private String determineCategory(List<WeeklyRecord> weeklyRecords) {
        boolean hasContributionAfterJuly = weeklyRecords.stream()
                .anyMatch(weeklyRecord -> weeklyRecord.getMonth() > 7 && weeklyRecord.getWageAmount().compareTo(BigDecimal.ZERO) > 0);
        return hasContributionAfterJuly ? "Category B" : "Category A";
    }
}
