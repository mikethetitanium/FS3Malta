package com.FS3Malta.FS3Malta.service;

import com.FS3Malta.FS3Malta.model.Employee;
import com.FS3Malta.FS3Malta.model.WeeklyWage;
import com.FS3Malta.FS3Malta.Repository.EmployeeRepository;
import com.FS3Malta.FS3Malta.Repository.WeeklyWageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WeeklyWageRepository weeklyWageRepository;

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if database is empty
        if (employeeRepository.count() > 0) {
            return;
        }

        // Create 5 employees
        Employee[] employees = {
                new Employee("Michael", "John", "123456A", "SS123456"),
                new Employee("Azhar", "Javed", "234567B", "SS234567"),
                new Employee("Katrin", "Rudi", "345678C", "SS345678"),
                new Employee("Sohail", "Ahmad", "456789D", "SS456789")
        };

        for (Employee emp : employees) {
            employeeRepository.save(emp);
        }

        // Generate 4 years of weekly wages for each employee
        for (Employee emp : employees) {
            List<WeeklyWage> wages = generateWeeklyWages(emp, 2022,2023, 2024, 2025);
            weeklyWageRepository.saveAll(wages);
        }
    }

   //Generate weekly wages with some variation around the base wage of 865.38
    private List<WeeklyWage> generateWeeklyWages(Employee employee, int... years) {
        List<WeeklyWage> wages = new ArrayList<>();
        BigDecimal baseWeeklyWage = new BigDecimal("865.38");

        for (int year : years) {
            for (int month = 1; month <= 12; month++) {
                int weeksInMonth = getWeeksInMonth(month);
                for (int week = 1; week <= weeksInMonth; week++) {
                    // Add slight variation to base wage 
                    BigDecimal variation = baseWeeklyWage.multiply(
                            new BigDecimal(0.95 + Math.random() * 0.10)
                    );
                    wages.add(new WeeklyWage(employee, year, month, week, variation));
                }
            }
        }

        return wages;
    }

    private int getWeeksInMonth(int month) {
        return (month == 2) ? 4 : (month % 2 == 0) ? 4 : 5;
    }
}
