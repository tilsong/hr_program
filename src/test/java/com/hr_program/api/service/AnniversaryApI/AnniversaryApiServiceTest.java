package com.hr_program.api.service.AnniversaryApI;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class AnniversaryApiServiceTest {

    @Autowired
    private AnniversaryApiService anniversaryApiService;

    @DisplayName("기념일 정보를 가져온다.")
    @Test
    void getAnniversaryInfoTest() {
        // given
        String year = "2023";
        String month = "07";

        // when
        var anniversaryInfo = anniversaryApiService.getAnniversaryInfo(year, month);

        // then
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(String.valueOf(20230707), formatter);
        LocalDate date2 = LocalDate.parse(String.valueOf(20230723), formatter);

        assertThat(anniversaryInfo).hasSize(2)
                .extracting("anniversaryDate", "anniversaryDateName", "isHoliday")
                .containsExactlyInAnyOrder(
                        tuple(date, "소서", false),
                        tuple(date2, "대서", false)
                );
    }

    @DisplayName("유효하지 않은 날짜 정보 입력 시 요청이 실패한다.")
    @Test
    void getAnniversaryInfoTestWithInvalidInput() {
        // given
        String invalidYear = "900";

        // when then
        assertThatThrownBy(() -> anniversaryApiService.getAnniversaryInfo(invalidYear, "07"))
                .isInstanceOf(InvalidFormatException.class);
    }
}