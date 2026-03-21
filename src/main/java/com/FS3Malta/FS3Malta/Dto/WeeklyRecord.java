package com.FS3Malta.FS3Malta.Dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeeklyRecord {
private Integer month;
private Integer week;
private BigDecimal wageAmount;
}
