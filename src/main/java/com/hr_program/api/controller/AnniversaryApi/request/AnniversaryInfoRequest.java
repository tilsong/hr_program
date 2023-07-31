package com.hr_program.api.controller.AnniversaryApi.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AnniversaryInfoRequest(
        @NotNull(message = "연도를 지정해야 합니다.")
        @Min(value = 2019, message = "연도는 2019 이상의 정수로 지정해야 합니다.")
        String year,
        @NotNull(message = "월을 지정해야 합니다.")
        @Min(value = 1, message = "월은 1~12 사이의 정수로 지정해야 합니다.")
        @Max(value = 12, message = "월은 1~12 사이의 정수로 지정해야 합니다.")
        String month
) {
}