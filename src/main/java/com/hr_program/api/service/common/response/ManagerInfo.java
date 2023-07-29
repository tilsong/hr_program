package com.hr_program.api.service.common.response;

import com.hr_program.domain.employee.Employee;
import lombok.Builder;

@Builder
public record ManagerInfo (
        Long id,
        String firstName,
        String lastName
) {
    public static ManagerInfo from(Employee employee) {
        if (employee == null) {
            return null;
        }

        return ManagerInfo.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .build();
    }
}

