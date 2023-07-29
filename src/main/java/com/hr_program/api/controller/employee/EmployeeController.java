package com.hr_program.api.controller.employee;

import com.hr_program.api.ApiResponse;
import com.hr_program.api.service.employee.response.EmployeeResponse;
import com.hr_program.api.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/api/employee/{employeeId}")
    public ApiResponse<EmployeeResponse> getEmployee(@PathVariable Long employeeId) {
        return ApiResponse.ok(employeeService.getEmployee(employeeId));
    }

}
