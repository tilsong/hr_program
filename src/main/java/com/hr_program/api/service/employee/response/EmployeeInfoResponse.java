package com.hr_program.api.service.employee.response;

import com.hr_program.domain.employee.Employee;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class EmployeeInfoResponse {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date hireDate;
    private BigDecimal salary;
    private BigDecimal commissionPct;

    private Long departmentId;
    private String departmentName;
    private String jobId;
    private String jobTitle;
    private Long managerId;
    private String managerFirstName;
    private String managerLastName;

    @Builder
    public EmployeeInfoResponse(Long employeeId, String firstName, String lastName, String email, String phoneNumber, Date hireDate, BigDecimal salary, BigDecimal commissionPct, Long departmentId, String departmentName, String jobId, String jobTitle, Long managerId, String managerFirstName, String managerLastName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.salary = salary;
        this.commissionPct = commissionPct;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.managerId = managerId;
        this.managerFirstName = managerFirstName;
        this.managerLastName = managerLastName;
    }

    public static EmployeeInfoResponse of(Employee employee) {
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
                .managerId(employee.getManager().getId())
                .managerFirstName(employee.getManager().getFirstName())
                .managerLastName(employee.getManager().getLastName())
                .build();
    }
}
