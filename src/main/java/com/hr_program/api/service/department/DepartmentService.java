package com.hr_program.api.service.department;

import com.hr_program.api.service.department.response.DepartmentInfoResponse;
import com.hr_program.domain.department.DepartmentRepository;
import com.hr_program.domain.department.exception.DepartmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentInfoResponse getDepartmentInfo(Long departmentId) {
        var department = departmentRepository.findDepartmentById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException(departmentId));

        return DepartmentInfoResponse.from(department);
    }
}
