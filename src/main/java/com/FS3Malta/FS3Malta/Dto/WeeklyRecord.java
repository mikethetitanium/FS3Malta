package com.FS3Malta.FS3Malta.Dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class WeeklyRecord {
private int wageMonth;
private int wageWeek;
private BigDecimal wageAmount;

 public WeeklyRecord(int month, int week, BigDecimal amount) {
        this.wageMonth = month;
        this.wageWeek = week;
        this.wageAmount = amount;
    }
}
