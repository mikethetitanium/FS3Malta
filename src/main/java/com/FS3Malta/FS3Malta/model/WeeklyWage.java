package com.FS3Malta.FS3Malta.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name = "weekly_wages")
public class WeeklyWage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weekly_wage_id")
    private Long id;

   

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "wage_year", nullable = false)
    private Integer wageYear;

    @Column(name = "wage_month", nullable = false)
    private Integer wageMonth;

    @Column(name = "wage_week", nullable = false)
    private Integer wageWeek;

    @Column(nullable = false)
    private BigDecimal wageAmount;

    public WeeklyWage() {}

    public WeeklyWage(Employee employee, int year, int month, int week, BigDecimal amount) {
        this.employee = employee;
        this.wageYear = year;
        this.wageMonth = month;
        this.wageWeek = week;
        this.wageAmount = amount;
    }
}
