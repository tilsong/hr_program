package com.hr_program.api.service.jobHistory;

import com.hr_program.api.service.jobHistory.response.JobHistoryResponse;
import com.hr_program.domain.department.Department;
import com.hr_program.domain.department.DepartmentRepository;
import com.hr_program.domain.employee.Employee;
import com.hr_program.domain.employee.EmployeeRepository;
import com.hr_program.domain.job.Job;
import com.hr_program.domain.job.JobRepository;
import com.hr_program.domain.jobHistory.JobHistory;
import com.hr_program.domain.jobHistory.JobHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.hr_program.util.TestUtil.*;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class JobHistoryServiceTest {

    @Autowired
    private JobHistoryService jobHistoryService;

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @DisplayName("사원의 아이디를 통해 사원의 이력 정보를 조회한다.")
    @Test
    void findJobHistoryByEmployeeId() {
        // given
        var department = createDepartment(1L, "Web");
        Department saveDepartment = departmentRepository.save(department);

        Job oldJob = createJob("TEMP_JOB", "Temp job");
        Job newJob = createJob("TEMP_JOB2", "Temp job2");
        jobRepository.saveAll(List.of(oldJob, newJob));

        Long employeeId = 101L;
        Employee employee = createEmployee(employeeId, newJob);
        Employee saveEmployee = employeeRepository.save(employee);

        Date oneYearAgo = getOneYearAgo();
        JobHistory jobHistory = createJobHistory(saveEmployee, oneYearAgo, new Date(), oldJob, saveDepartment);
        jobHistoryRepository.save(jobHistory);

        // when
        List<JobHistoryResponse> findJobHistory = jobHistoryService.getJobHistory(employeeId);

        // then
        assertThat(findJobHistory).hasSize(1)
                .extracting("jobId", "jobTitle", "departmentId", "departmentName")
                .containsExactlyInAnyOrder(
                        tuple(oldJob.getJobId(), oldJob.getJobTitle(), saveDepartment.getDepartmentId(), saveDepartment.getDepartmentName())
                );
    }
}