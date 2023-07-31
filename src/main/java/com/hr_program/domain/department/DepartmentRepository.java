package com.hr_program.domain.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select distinct d from Department d left join fetch d.manager left join fetch d.location l join fetch l.country c join fetch c.region where d.departmentId = :departmentId")
    Optional<Department> findDepartmentById(Long departmentId);
}
