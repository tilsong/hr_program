package com.hr_program.domain.employee.exception;

import lombok.Getter;

@Getter
public class InvalidSalaryRaiseRateException extends RuntimeException {

    private final Double requestedRaiseRate;

    private final static String message = "급여 인상률이 유효하지 않은 값입니다.\n0 보다 크고 100 보다 작은 값을 입력해주세요.";

    public InvalidSalaryRaiseRateException(Double requestedRaiseRate) {
        super(message);
        this.requestedRaiseRate = requestedRaiseRate;
    }
}
