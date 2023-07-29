package com.hr_program.api.service.department.response;

import com.hr_program.api.service.common.response.ManagerInfo;
import com.hr_program.domain.department.Department;
import lombok.Builder;

import java.util.Optional;

@Builder
public record DepartmentInfoResponse (
        Long departmentId,
        String departmentName,
        ManagerInfo manager,
        LocationInfo location
){
    public static DepartmentInfoResponse from(Department department) {
        return DepartmentInfoResponse.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .manager(Optional.ofNullable(department.getManager()).map(ManagerInfo::from).orElse(null))
                .location(Optional.ofNullable(department.getLocation()).map(LocationInfo::from).orElse(null))
                .build();
    }
}
