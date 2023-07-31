package com.hr_program.api.service.AnniversaryApI.response;

import com.hr_program.api.intergration.response.ResponseData;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AnniversaryInfoResponse(
        LocalDate anniversaryDate,
        String anniversaryDateName,
        boolean isHoliday
) {
    public static AnniversaryInfoResponse from(ResponseData.Response.Body.Items.Item anniversaryInfo) {
        return  AnniversaryInfoResponse.builder()
                .anniversaryDate(anniversaryInfo.getLocdate())
                .anniversaryDateName(anniversaryInfo.getDateName())
                .isHoliday(anniversaryInfo.getIsHoliday())
                .build();
    }
}
