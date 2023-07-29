package com.hr_program.api.service.employee.response;

import com.hr_program.api.service.common.response.ManagerInfo;
import com.hr_program.domain.employee.Employee;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Builder
public record EmployeeInfoResponse(
    Long employeeId,
    String firstName,
    String lastName,
    String email,
    String phoneNumber,
    Date hireDate,
    BigDecimal salary,
    BigDecimal commissionPct,
    Long departmentId,
    String departmentName,
    String jobId,
    String jobTitle,
    ManagerInfo managerInfo
) {
    public static EmployeeInfoResponse from(Employee employee) {
        return EmployeeInfoResponse.builder()
                .employeeId(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .hireDate(employee.getHireDate())
                .salary(employee.getSalary())
                .commissionPct(employee.getCommissionPct())
                .departmentId(employee.getDepartment().getDepartmentId())
                .departmentName(employee.getDepartment().getDepartmentName())
                .jobId(employee.getJob().getJobId())
                .jobTitle(employee.getJob().getJobTitle())
                .managerInfo(Optional.ofNullable(employee.getManager()).map(ManagerInfo::from).orElse(null))
                .build();
    }
}
