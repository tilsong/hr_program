package com.hr_program.domain.employee;

import com.hr_program.api.service.department.DepartmentService;
import com.hr_program.domain.department.Department;
import com.hr_program.domain.department.DepartmentRepository;
import com.hr_program.domain.employee.exception.EmployeeNotFoundException;
import com.hr_program.domain.job.Job;
import com.hr_program.domain.job.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JobRepository jobRepository;

    @DisplayName("사원의 아이디를 통해 사원의 현재 정보를 조회한다.")
    @Test
    void findEmployeeByEmployeeId() {
        // given
        Long employeeId = 110L;

        // when
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(null);

        // then
        assertThat(employee.getId()).isEqualTo(employeeId);
    }

    @DisplayName("존재하지 않는 사원의 아이디를 통해 사원의 현재 정보를 조회하면 실패한다.")
    @Test
    void findEmployeeByNoexistsEmployeeId() {
        // given
        Long employeeId = 90L;

        // when then
        assertThatThrownBy(() -> employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId)))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("존재하지 않는 직원 ID입니다.");
    }

    @DisplayName("부서 아이디를 통해 사원들의 정보를 조회한다.")
    @Test
    void findAllByDepartmentId() {
        // given
        Long departmentId = 1L;
        var department = createDepartment(departmentId, "Web");
        departmentRepository.save(department);

        Job job = createJob();
        jobRepository.save(job);

        Employee employee1 = createEmployee(1L, department, job);
        Employee employee2 = createEmployee(2L, department, job);
        employeeRepository.saveAll(List.of(employee1, employee2));

        // when
        List<Employee> allBy = employeeRepository.findAllByDepartmentId(departmentId);

        // then
        assertThat(allBy).hasSize(2)
                .extracting("id", "department.departmentId")
                .containsExactlyInAnyOrder(
                        tuple(employee1.getId(), departmentId),
                        tuple(employee2.getId(), departmentId)
                );
    }

    private static Job createJob() {
        return Job.builder()
                .jobId("TEMP_JOB")
                .jobTitle("Temp job")
                .build();
    }

    private static Employee createEmployee(Long id, Department department, Job job) {
        return Employee.builder()
                .id(id)
                .lastName("kim")
                .email("kim")
                .hireDate(new Date())
                .salary(new BigDecimal(1000.0))
                .department(department)
                .job(job)
                .build();
    }

    private static Department createDepartment(Long departmentId, String departmentName) {
        return Department.builder()
                .departmentId(departmentId)
                .departmentName(departmentName)
                .build();
    }
}