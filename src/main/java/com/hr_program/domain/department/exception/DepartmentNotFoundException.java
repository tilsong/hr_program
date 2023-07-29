package com.hr_program.domain.department.exception;

import lombok.Getter;

@Getter
public class DepartmentNotFoundException extends RuntimeException {

    private Long requestedDepartmentId;

    private final static String message = "존재하지 않는 부서 Id입니다.";

    public DepartmentNotFoundException(Long requestedDepartmentId) {
        super(message);
        this.requestedDepartmentId = requestedDepartmentId;
    }
}
