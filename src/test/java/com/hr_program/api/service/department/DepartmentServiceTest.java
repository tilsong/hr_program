package com.hr_program.api.service.department;

import com.hr_program.api.service.department.response.DepartmentInfoResponse;
import com.hr_program.api.service.department.response.RaiseSalariesForDepartmentResponse;
import com.hr_program.domain.department.Department;
import com.hr_program.domain.department.DepartmentRepository;
import com.hr_program.domain.department.exception.DepartmentNotFoundException;
import com.hr_program.domain.employee.Employee;
import com.hr_program.domain.employee.EmployeeRepository;
import com.hr_program.domain.employee.exception.InvalidSalaryRaiseRateException;
import com.hr_program.domain.job.Job;
import com.hr_program.domain.job.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @DisplayName("부서 아이디를 통해 부서 및 위치 정보를 조회한다.")
    @Test
    void findDepartmentAndLocationInfoByDepartmentId() {
        // given
        Long departmentId = 10L;
        Long mappedLocationId = 1700L;
        Long mappedManagerId = 200L;

        // when
        DepartmentInfoResponse departmentInfo = departmentService.getDepartmentInfo(departmentId);

        // then
        assertThat(departmentInfo.departmentId()).isEqualTo(departmentId);
        assertThat(departmentInfo.location().id()).isEqualTo(mappedLocationId);
        assertThat(departmentInfo.manager().id()).isEqualTo(mappedManagerId);
    }

    @DisplayName("존재하지 않는 부서 아이디를 통해 부서 및 위치 정보를 조회하면 실패한다.")
    @Test
    void findDepartmentAndLocationInfoByNoexistsDepartmentId() {
        // given
        Long departmentId = 0L;

        // when then
        assertThatThrownBy(() -> departmentService.getDepartmentInfo(departmentId))
                .isInstanceOf(DepartmentNotFoundException.class)
                .hasMessage("존재하지 않는 부서 Id입니다.");
    }

    @DisplayName("부서 아이디를 통해 부서 직원들의 급여를 특정 비율로 인상한다.")
    @Test
    void raiseSalariesForDepartment() {
        // given
        Double raiseRate = 2.5;

        Department dep = createDepartment(1L, "Web");
        departmentRepository.save(dep);

        Job job = createJob("WebProg", 105.0);
        jobRepository.save(job);

        Employee employee1 = createEmployee(1L, dep, job, 90.0);
        Employee employee2 = createEmployee(2L, dep, job, 100.0);
        employeeRepository.saveAll(List.of(employee1, employee2));

        // when
        List<RaiseSalariesForDepartmentResponse> responses = departmentService.raiseSalariesForDepartment(dep.getDepartmentId(), raiseRate);

        // then
        assertThat(responses).hasSize(2)
                .extracting("employeeId", "raisedSalary", "isMaxSalary")
                .containsExactlyInAnyOrder(
                        tuple(1L, new BigDecimal(92.25).setScale(5), false),
                        tuple(2L, new BigDecimal(102.5).setScale(5), false)
                );
    }

    @DisplayName("급여 인상 비율이 0보다 크고 100 보다 작지 않은 값이면 예외가 발생한다.")
    @Test
    void raiseSalaryWithInvalidRaiseRate() {
        // given
        Double raiseRate = 110.0;

        Department dep = createDepartment(1L, "Web");
        departmentRepository.save(dep);

        Job job = createJob("WebProg", 105.0);
        jobRepository.save(job);

        Employee employee = createEmployee(2L, dep, job, 100.0);
        employeeRepository.save(employee);

        // when then
        assertThatThrownBy(() -> departmentService.raiseSalariesForDepartment(dep.getDepartmentId(), raiseRate))
                .isInstanceOf(InvalidSalaryRaiseRateException.class)
                .hasMessage("급여 인상률이 유효하지 않은 값입니다.\n0 보다 크고 100 보다 작은 값을 입력해주세요.");
    }

    private static Job createJob(String jobName, Double maxSalary) {
        return Job.builder()
                .jobId(jobName)
                .jobTitle(jobName)
                .maxSalary(new BigDecimal(maxSalary))
                .build();
    }

    private static Employee createEmployee(Long id, Department department, Job job, Double salary) {
        return Employee.builder()
                .id(id)
                .lastName("kim")
                .email("kim")
                .hireDate(new Date())
                .salary(new BigDecimal(salary))
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