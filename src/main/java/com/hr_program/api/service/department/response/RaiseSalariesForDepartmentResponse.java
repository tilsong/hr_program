package com.hr_program.api.service.department.response;

import com.hr_program.domain.employee.Employee;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RaiseSalariesForDepartmentResponse(
    Long employeeId,
    BigDecimal raisedSalary,
    boolean isMaxSalary
) {
    public static RaiseSalariesForDepartmentResponse from(Employee employee) {
        return RaiseSalariesForDepartmentResponse.builder()
                .employeeId(employee.getId())
                .raisedSalary(employee.getSalary())
                .isMaxSalary(employee.isMaxSalary())
                .build();
    }
}
