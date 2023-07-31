package com.hr_program.api.intergration;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hr_program.api.intergration.response.ResponseData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class AnniversaryApiClientTest {

    @Autowired
    private AnniversaryApiClient anniversaryApiClient;

    @DisplayName("기념일 정보를 가져온다.")
    @Test
    void getAnniversaryInfoTest() {
        // given
        String year = "2023";
        String month = "07";

        // when
        var anniversaryInfo = anniversaryApiClient.getAnniversaryInfo(year, month);

        // then
        ResponseData.Response.Body.Items.Item item1 = anniversaryInfo.get(0);
        ResponseData.Response.Body.Items.Item item2 = anniversaryInfo.get(1);
        assertThat(item1.getLocdate()).isEqualTo("2023-07-07");
        assertThat(item1.getDateName()).isEqualTo("소서");
        assertThat(item1.getIsHoliday()).isFalse();
        assertThat(item2.getLocdate()).isEqualTo("2023-07-23");
        assertThat(item2.getDateName()).isEqualTo("대서");
        assertThat(item2.getIsHoliday()).isFalse();
    }

    @DisplayName("유효하지 않은 날짜 정보 입력 시 요청이 실패한다.")
    @Test
    void getAnniversaryInfoTestWithInvalidInput() {
        // given
        String invalidYear = "1000";

        // when
        assertThatThrownBy(() -> anniversaryApiClient.getAnniversaryInfo(invalidYear, "07"))
                .isInstanceOf(InvalidFormatException.class);
    }
}