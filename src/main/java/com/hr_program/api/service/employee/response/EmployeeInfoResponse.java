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
    DepartmentInfo departmentInfo,
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
                .departmentInfo(Optional.ofNullable(employee.getDepartment()).map(DepartmentInfo::from).orElse(null))
                .jobId(employee.getJob().getJobId())
                .jobTitle(employee.getJob().getJobTitle())
                .managerInfo(Optional.ofNullable(employee.getManager()).map(ManagerInfo::from).orElse(null))
                .build();
    }
}
