package com.hr_program.api.controller.employee;

import com.hr_program.api.service.employee.EmployeeService;
import com.hr_program.api.service.employee.response.EmployeeInfoResponse;
import com.hr_program.domain.employee.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @DisplayName("사원의 아이디를 통해 사원의 현재 정보를 조회한다.")
    @Test
    void findEmployeeByEmployeeId() throws Exception {
        // given
        Long employeeId = 100L;
        String departmentName = "Executive";
        String jobTitle = "President";
        EmployeeInfoResponse employeeResponse = createEmployeeInfoResponse(employeeId, departmentName, jobTitle);

        when(employeeService.getEmployeeInfo(employeeId)).thenReturn(employeeResponse);

        // When Then
        mockMvc.perform(get("/api/employee/{employeeId}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.employeeId").value(employeeId))
                .andExpect(jsonPath("$.data.jobTitle").value("President"))
                .andExpect(jsonPath("$.data.departmentName").value("Executive"));
    }

    @DisplayName("존재하지 않는 사원의 아이디를 통해 사원의 현재 정보를 조회하면 실패한다.")
    @Test
    void findEmployeeByNoexistsEmployeeId() throws Exception {
        // given
        Long employeeId = 90L;

        when(employeeService.getEmployeeInfo(employeeId)).thenThrow(new EmployeeNotFoundException(employeeId));

        // When Then
        mockMvc.perform(get("/api/employee/{employeeId}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 직원 ID입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    private static EmployeeInfoResponse createEmployeeInfoResponse(Long employeeId, String departmentName, String jobTitle) {
        return EmployeeInfoResponse.builder()
                .employeeId(employeeId)
                .departmentName("Executive")
                .jobTitle("President")
                .build();
    }
}