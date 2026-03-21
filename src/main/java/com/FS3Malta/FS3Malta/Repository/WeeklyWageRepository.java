package com.FS3Malta.FS3Malta.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.FS3Malta.FS3Malta.model.WeeklyWage;

public interface WeeklyWageRepository extends JpaRepository<WeeklyWage, Long> {
@Query("SELECT ww FROM WeeklyWage ww WHERE ww.employee.id = :employeeId AND ww.year = :year ORDER BY ww.month, ww.week")
    List<WeeklyWage> findByEmployeeIdAndYear(@Param("employeeId") Long employeeId, @Param("year") Integer year);
    
    @Query("SELECT ww FROM WeeklyWage ww WHERE ww.employee.id = :employeeId ORDER BY ww.year DESC, ww.month DESC")
    List<WeeklyWage> findByEmployeeId(@Param("employeeId") Long employeeId);
    
    @Query(value = "SELECT COUNT(*) FROM weekly_wages WHERE employee_id = :employeeId AND wage_year = :year AND amount > 0", nativeQuery = true)
    Integer countContributingWeeks(@Param("employeeId") Long employeeId, @Param("year") Integer year);
    
    @Query(value = "SELECT SUM(amount) FROM weekly_wages WHERE employee_id = :employeeId AND wage_year = :year", nativeQuery = true)
    java.math.BigDecimal sumWagesByEmployeeAndYear(@Param("employeeId") Long employeeId, @Param("year") Integer year);
}
