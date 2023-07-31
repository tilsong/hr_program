package com.hr_program.api.service.employee;

import com.hr_program.api.service.employee.response.EmployeeInfoResponse;
import com.hr_program.domain.employee.Employee;
import com.hr_program.domain.employee.EmployeeRepository;
import com.hr_program.domain.employee.exception.EmployeeNotFoundException;
import com.hr_program.domain.job.Job;
import com.hr_program.domain.job.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.hr_program.util.TestUtil.createEmployee;
import static com.hr_program.util.TestUtil.createJob;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobRepository jobRepository;

    @DisplayName("사원의 아이디를 통해 사원의 현재 정보를 조회한다.")
    @Test
    void findEmployeeByEmployeeId() {
        // given
        Job job = createJob("TEMP_JOB", "Temp job");
        Job saveJob = jobRepository.save(job);

        Long employeeId = 2L;
        Employee employee = createEmployee(employeeId, saveJob);
        Employee save = employeeRepository.save(employee);

        // when
        EmployeeInfoResponse response = employeeService.getEmployeeInfo(employeeId);

        // then
        assertThat(response.employeeId()).isNotNull();
        assertThat(response)
                .extracting("firstName", "lastName", "email", "jobId", "jobTitle")
                .contains(save.getFirstName(), save.getLastName(), save.getEmail(), saveJob.getJobId(), saveJob.getJobTitle());
    }

    @DisplayName("존재하지 않는 사원의 아이디를 통해 사원의 현재 정보를 조회하면 실패한다.")
    @Test
    void findEmployeeByNoexistsEmployeeId() {
        // given
        Long employeeId = 0L;

        // when then
        assertThatThrownBy(() -> employeeService.getEmployeeInfo(employeeId))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("존재하지 않는 직원 ID입니다.");
    }

}