package com.hr_program.api;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hr_program.domain.department.exception.DepartmentNotFoundException;
import com.hr_program.domain.employee.exception.EmployeeNotFoundException;
import com.hr_program.domain.employee.exception.InvalidSalaryRaiseRateException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e) {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
            null
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ApiResponse<Object> employeeNotFoundException(EmployeeNotFoundException e) {
        return ApiResponse.of(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                null
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ApiResponse<Object> departmentNotFoundException(DepartmentNotFoundException e) {
        return ApiResponse.of(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                null
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidSalaryRaiseRateException.class)
    public ApiResponse<Object> invalidSalaryRaiseRateException(InvalidSalaryRaiseRateException e) {
        return ApiResponse.of(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                null
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public ApiResponse<Object> invalidFormatException(InvalidFormatException e) {
        return ApiResponse.of(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                null
        );
    }
}
