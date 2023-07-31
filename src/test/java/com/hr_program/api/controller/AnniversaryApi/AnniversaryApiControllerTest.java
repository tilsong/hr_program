package com.hr_program.api.controller.AnniversaryApi;

import com.hr_program.api.controller.AnniversaryApi.request.AnniversaryInfoRequest;
import com.hr_program.api.service.AnniversaryApI.AnniversaryApiService;
import com.hr_program.api.service.AnniversaryApI.response.AnniversaryInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AnniversaryApiController.class)
class AnniversaryApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnniversaryApiService anniversaryApiService;

    @DisplayName("연도와 월 정보를 통해 기념일 정보를 가져온다.")
    @Test
    void getAnniversaryInfoTest() throws Exception {
        // given
        var request = createRequest("2023", "07");

        AnniversaryInfoResponse response = createResponse(getLocalDate(20230707));
        AnniversaryInfoResponse response2 = createResponse(getLocalDate(20230720));
        List<AnniversaryInfoResponse> responses = List.of(response, response2);

        when(anniversaryApiService.getAnniversaryInfo(request.year(), request.month()))
                .thenReturn(responses);

        // When Then
        mockMvc.perform(get("/api/anniversary")
                        .param("year", request.year())
                        .param("month", request.month())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("유효하지 않은 날짜 정보 입력 시 요청이 실패한다.")
    @Test
    void getAnniversaryInfoTestWithInvalidInput() throws Exception {
        // given
        var request = createRequest("100", "07");

        // When Then
        mockMvc.perform(get("/api/anniversary")
                        .param("year", request.year())
                        .param("month", request.month())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("연도는 2019 이상의 정수로 지정해야 합니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    private static AnniversaryInfoRequest createRequest(String year, String month) {
        return AnniversaryInfoRequest.builder()
                .year(year)
                .month(month)
                .build();
    }

    private static LocalDate getLocalDate(int date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(String.valueOf(date), formatter);
        return localDate;
    }

    private static AnniversaryInfoResponse createResponse(LocalDate date) {
        return AnniversaryInfoResponse.builder()
                .anniversaryDate(date)
                .anniversaryDateName("대서")
                .isHoliday(false)
                .build();
    }

}