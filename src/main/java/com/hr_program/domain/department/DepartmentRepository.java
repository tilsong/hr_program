package com.hr_program.domain.department;

import com.hr_program.domain.jobHistory.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select distinct d from Department d join fetch d.manager join fetch d.location l join fetch l.country c join fetch c.region where d.departmentId = :departmentId")
    Optional<Department> findDepartmentById(Long departmentId);
}
