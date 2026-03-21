package com.FS3Malta.FS3Malta.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "employees")
@AllArgsConstructor

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String idCard;

    @Column(nullable = false)
    private String socialSecurityNo;

@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WeeklyWage> weeklyWages;
     public Employee() {}

    public Employee(String surname, String firstName, String idCard, String socialSecurityNo) {
        this.surname = surname;
        this.firstName = firstName;
        this.idCard = idCard;
        this.socialSecurityNo = socialSecurityNo;
    }

    public List<WeeklyWage> getWeeklyWages() {
        return weeklyWages;
    }

    public void setWeeklyWages(List<WeeklyWage> weeklyWages) {
        this.weeklyWages = weeklyWages;
    }
}
