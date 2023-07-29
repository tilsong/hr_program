package com.hr_program.api.service.employee;


import com.hr_program.api.service.employee.response.EmployeeResponse;
import com.hr_program.domain.employee.EmployeeRepository;
import com.hr_program.domain.employee.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @DisplayName("사원의 아이디를 통해 사원의 현재 정보를 조회한다.")
    @Test
    void findEmployeeByEmployeeId() {
        // given
        Long employeeId = 110L;

        // when
        EmployeeResponse response = employeeService.getEmployee(employeeId);

        // then
        assertThat(response.getEmployeeId()).isNotNull();
        assertThat(response)
                .extracting("firstName", "lastName", "email", "departmentId", "departmentName", "jobId", "jobTitle", "managerId")
                .contains("John", "Chen", "JCHEN", 100L, "Finance", "FI_ACCOUNT", "Accountant", 108L);
    }

    @DisplayName("존재하지 않는 사원의 아이디를 통해 사원의 현재 정보를 조회하면 실패한다.")
    @Test
    void findEmployeeByNoexistsEmployeeId() {
        // given
        Long employeeId = 90L;

        // when then
        assertThatThrownBy(() -> employeeService.getEmployee(employeeId))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("존재하지 않는 직원 ID입니다.");
    }

}