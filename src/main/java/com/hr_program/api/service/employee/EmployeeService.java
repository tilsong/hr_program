package com.hr_program.api.service.employee;

import com.hr_program.api.service.employee.response.EmployeeResponse;
import com.hr_program.domain.department.Department;
import com.hr_program.domain.employee.Employee;
import com.hr_program.domain.employee.EmployeeRepository;
import com.hr_program.domain.employee.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeResponse getEmployee(Long employeeId) {
        var employee = employeeRepository.findByIdWithFetchJoin(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        return EmployeeResponse.of(employee);
    }
}
