package com.FS3Malta.FS3Malta.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.FS3Malta.FS3Malta.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
@Query("SELECT e FROM Employee e WHERE e.idCard = :idCard")
    Employee findByIdCard(@Param("idCard") String idCard);
}
