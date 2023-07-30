package com.hr_program.domain.employee;

import com.hr_program.domain.job.Job;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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
