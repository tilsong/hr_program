package com.hr_program.domain.employee;

import com.hr_program.domain.employee.exception.InvalidSalaryRaiseRateException;
import com.hr_program.domain.job.Job;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmployeeTest {

    @DisplayName("현재 직원의 봉급이 최대 값이면 참을 반환한다.")
    @Test
    void isMaxSalary() {
        // given
        Job job = createJob("WebProgrammer", 10000.0);
        Employee employee = createEmployee(job, 10000.0);

        // when
        boolean isMaxSalary = employee.isMaxSalary();

        // then
        assertThat(isMaxSalary).isTrue();
    }

    @DisplayName("현재 직원의 봉급이 최대 값이 아니면 거짓을 반환한다.")
    @Test
    void isNoMaxSalary() {
        // given
        Job job = createJob("WebProgrammer", 10000.0);
        Employee employee = createEmployee(job, 9000.0);

        // when
        boolean isMaxSalary = employee.isMaxSalary();

        // then
        assertThat(isMaxSalary).isFalse();
    }

    @DisplayName("특정 사원의 급여를 특정 비율로 인상한다.")
    @Test
    void raiseSalary() {
        // given
        Double raiseRate = 2.5;

        Job job = createJob("WebProgrammer", 105.0);
        Employee employee = createEmployee(job, 100.0);

        // when
        BigDecimal raisedSalary = employee.raiseSalary(raiseRate);

        // then
        assertThat(raisedSalary).isEqualByComparingTo(BigDecimal.valueOf(102.5));
    }

    @DisplayName("특정 사원의 인상된 급여가 Job의 최대 값 이상이면 Job의 최대 급여를 반환한다.")
    @Test
    void raiseSalaryWithMaxSalary() {
        // given
        Double raiseRate = 2.5;

        Job job = createJob("WebProgrammer", 105.0);
        Employee employee = createEmployee(job, 104.0);

        // when
        BigDecimal raisedSalary = employee.raiseSalary(raiseRate);

        // then
        assertThat(raisedSalary).isEqualByComparingTo(BigDecimal.valueOf(105.0));
    }

    @DisplayName("급여 인상 비율이 0보다 크고 100 보다 작지 않은 값이면 예외가 발생한다.")
    @Test
    void raiseSalaryWithInvalidRaiseRate() {
        // given
        Double raiseRate = 111.0;

        Job job = createJob("WebProgrammer", 105.0);
        Employee employee = createEmployee(job, 100.0);

        // when then
        assertThatThrownBy(() -> employee.raiseSalary(raiseRate))
                .isInstanceOf(InvalidSalaryRaiseRateException.class)
                .hasMessage("급여 인상률이 유효하지 않은 값입니다.\n0 보다 크고 100 보다 작은 값을 입력해주세요.");
    }

    private static Job createJob(String jobName, Double maxSalary) {
        return Job.builder()
                .jobId(jobName)
                .maxSalary(new BigDecimal(maxSalary))
                .build();
    }

    private static Employee createEmployee(Job job, Double salary) {
        return Employee.builder()
                .id(10L)
                .job(job)
                .salary(new BigDecimal(salary))
                .build();
    }
}
