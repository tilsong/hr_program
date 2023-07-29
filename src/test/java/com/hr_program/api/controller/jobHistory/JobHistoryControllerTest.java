package com.hr_program.api.controller.jobHistory;

import com.hr_program.api.service.jobHistory.JobHistoryService;
import com.hr_program.api.service.jobHistory.response.JobHistoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JobHistoryController.class)
class JobHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobHistoryService jobHistoryService;

    @DisplayName("사원의 아이디를 통해 사원의 이력 정보를 조회한다.")
    @Test
    void findEmployeeByEmployeeId() throws Exception {
        // given
        Long employeeId = 101L;
        List<JobHistoryResponse> result = List.of();

        when(jobHistoryService.getJobHistory(employeeId)).thenReturn(result);

        // When Then
        mockMvc.perform(get("/api/employee/{employeeId}/history", employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray());
    }
}