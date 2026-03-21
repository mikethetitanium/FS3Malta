package com.FS3Malta.FS3Malta.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
}
