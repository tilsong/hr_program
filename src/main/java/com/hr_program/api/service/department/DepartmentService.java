package com.hr_program.api.service.department;

import com.hr_program.api.service.department.response.DepartmentInfoResponse;
import com.hr_program.api.service.department.response.RaiseSalariesForDepartmentResponse;
import com.hr_program.domain.department.DepartmentRepository;
import com.hr_program.domain.department.exception.DepartmentNotFoundException;
import com.hr_program.domain.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;

    public DepartmentInfoResponse getDepartmentInfo(Long departmentId) {
        var department = departmentRepository.findDepartmentById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException(departmentId));

        return DepartmentInfoResponse.from(department);
    }

    public List<RaiseSalariesForDepartmentResponse> raiseSalariesForDepartment(Long departmentId, Double raiseRate) {
        return employeeRepository.findAllByDepartmentId(departmentId)
                .stream()
                        .map(employee -> {
                            employee.raiseSalary(raiseRate);
                            return RaiseSalariesForDepartmentResponse.from(employee);
                        })
                        .collect(Collectors.toList());
    }
}
