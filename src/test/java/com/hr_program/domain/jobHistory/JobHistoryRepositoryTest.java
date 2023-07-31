package com.hr_program.domain.jobHistory;

import com.hr_program.domain.department.Department;
import com.hr_program.domain.department.DepartmentRepository;
import com.hr_program.domain.employee.Employee;
import com.hr_program.domain.employee.EmployeeRepository;
import com.hr_program.domain.job.Job;
import com.hr_program.domain.job.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static com.hr_program.util.TestUtil.*;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JobHistoryRepositoryTest {

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @DisplayName("사원의 아이디를 통해 사원의 이력 정보를 조회한다.")
    @Test
    void findJobHistoryByEmployeeId() {
        // given
        Department department = createDepartment(0L, "Web");
        Department saveDepartment = departmentRepository.save(department);

        Job olbJob = createJob("TEMP_JOB", "Temp job");
        Job newJob = createJob("TEMP_JOB2", "Temp job2");
        jobRepository.saveAll(List.of(olbJob, newJob));

        Long employeeId = 101L;
        Employee employee = createEmployee(employeeId, newJob);
        Employee saveEmployee = employeeRepository.save(employee);

        Date oneYearAgo = getOneYearAgo();
        JobHistory jobHistory = createJobHistory(saveEmployee, oneYearAgo, new Date(), olbJob, saveDepartment);
        jobHistoryRepository.save(jobHistory);

        // when
        List<JobHistory> responses = jobHistoryRepository.findJobHistoryByEmployeeId(employeeId);

        // then
        assertThat(responses).hasSize(1)
                        .extracting("employee.id", "job.jobId")
                                .containsExactlyInAnyOrder(
                                        tuple(101L, olbJob.getJobId())
                                );
    }


}