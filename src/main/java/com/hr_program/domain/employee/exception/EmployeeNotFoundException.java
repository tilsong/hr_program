package com.hr_program.domain.employee.exception;

import lombok.Getter;

@Getter
public class EmployeeNotFoundException extends RuntimeException {

    private Long requestedEmployeeId;

    private final static String message = "존재하지 않는 ID입니다.";

    public EmployeeNotFoundException(Long requestedEmployeeId) {
        super(message);
        this.requestedEmployeeId = requestedEmployeeId;
    }
}
