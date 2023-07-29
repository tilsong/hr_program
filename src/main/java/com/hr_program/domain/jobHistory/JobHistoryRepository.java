package com.hr_program.domain.jobHistory;

import com.hr_program.api.service.jobHistory.response.JobHistoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobHistoryRepository extends JpaRepository<JobHistory, Long> {
    @Query("select jh from JobHistory jh join fetch jh.job join fetch jh.department where jh.employee.id = :employeeId")
    List<JobHistory> findJobHistoryByEmployeeId(Long employeeId);
}
