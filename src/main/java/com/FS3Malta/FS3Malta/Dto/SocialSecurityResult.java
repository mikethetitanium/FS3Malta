package com.FS3Malta.FS3Malta.Dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SocialSecurityResult {
private BigDecimal totalWages;
private int weeksContributed;
private String category;
}
