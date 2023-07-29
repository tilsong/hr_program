package com.hr_program.domain.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e join fetch e.job join fetch e.manager join fetch e.department where e.id = :id")
    Optional<Employee> findByIdWithFetchJoin(@Param("id") Long id);
}
