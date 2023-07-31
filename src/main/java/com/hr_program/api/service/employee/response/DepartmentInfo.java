package com.hr_program.api.service.employee.response;

import com.hr_program.domain.department.Department;
import lombok.Builder;

@Builder
public record DepartmentInfo (
        Long departmentId,
        String departmentName
){
    public static DepartmentInfo from(Department department) {
        if (department == null) {
            return null;
        }

        return DepartmentInfo.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .build();
    }
}
