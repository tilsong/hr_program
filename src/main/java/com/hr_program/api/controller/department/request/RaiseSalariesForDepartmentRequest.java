package com.hr_program.api.controller.department.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RaiseSalariesForDepartmentRequest {
    @NotNull(message = "부서 아이디를 지정해야 합니다.")
    @Positive(message = "부서 아이디는 양의 정수여야 합니다.")
    private Long departmentId;

    @DecimalMin(value = "0.01", message = "인상률은 0보다 커야 합니다.")
    @DecimalMax(value = "99.99", message = "인상률은 100보다 작아야 합니다.")
    private Double raiseRate;
}