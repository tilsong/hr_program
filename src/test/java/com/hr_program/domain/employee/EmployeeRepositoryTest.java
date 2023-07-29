package com.hr_program.domain.employee;

import com.hr_program.domain.employee.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

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
                .isInstanceOf(EmployeeNotFoundException.class);
    }
}