package com.hr_program.api.controller.department;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr_program.api.controller.department.request.RaiseSalariesForDepartmentRequest;
import com.hr_program.api.service.department.DepartmentService;
import com.hr_program.api.service.department.response.DepartmentInfoResponse;
import com.hr_program.api.service.department.response.RaiseSalariesForDepartmentResponse;
import com.hr_program.domain.department.exception.DepartmentNotFoundException;
import com.hr_program.domain.employee.exception.InvalidSalaryRaiseRateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DepartmentService departmentService;

    @DisplayName("부서 아이디를 통해 부서 및 위치 정보를 조회한다.")
    @Test
    void findDepartmentAndLocationInfoByDepartmentId() throws Exception {
        // given
        Long departmentId = 10L;
        DepartmentInfoResponse departmentInfoResponse = createDepartmentInfoResponse(departmentId);

        when(departmentService.getDepartmentInfo(departmentId))
                .thenReturn(departmentInfoResponse);

        // When Then
        mockMvc.perform(get("/api/department/{departmentId}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.departmentId").value(departmentId));
    }

    @DisplayName("존재하지 않는 부서 아이디를 통해 부서 및 위치 정보를 조회하면 실패한다.")
    @Test
    void findDepartmentAndLocationInfoByNoexistsDepartmentId() throws Exception {
        // given
        Long departmentId = 9L;

        when(departmentService.getDepartmentInfo(departmentId))
                .thenThrow(new DepartmentNotFoundException(departmentId));

        // When Then
        mockMvc.perform(get("/api/department/{departmentId}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 부서 Id입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("부서 아이디를 통해 부서 직원들의 급여를 특정 비율로 인상한다.")
    @Test
    void raiseSalariesForDepartment() throws Exception {
        // given
        var response1 = createResponse(1L, 100.0);
        var response2 = createResponse(2L, 110.0);
        List<RaiseSalariesForDepartmentResponse> responses = List.of(response1, response2);

        when(departmentService.raiseSalariesForDepartment(10L, 2.5))
                .thenReturn(responses);

        var request = new RaiseSalariesForDepartmentRequest(10L, 1.0);

        // When Then
        mockMvc.perform(post("/api/department/raiseSalariesForDepartment")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("잘못된 급여 인상률을 입력하면 실패한다.")
    @Test
    void raiseSalariesForDepartmentWithInvalidRaiseRate() throws Exception {
        // given
        var request = new RaiseSalariesForDepartmentRequest(10L, -1.0);

        when(departmentService.raiseSalariesForDepartment(10L, -1.0))
                .thenThrow(new InvalidSalaryRaiseRateException(-1.0));

        // When Then
        mockMvc.perform(post("/api/department/raiseSalariesForDepartment")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("인상률은 0보다 커야 합니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    private static RaiseSalariesForDepartmentResponse createResponse(Long employeeId, Double raisedSalary) {
        return RaiseSalariesForDepartmentResponse.builder()
                .employeeId(employeeId)
                .raisedSalary(new BigDecimal(raisedSalary))
                .build();
    }

    private static DepartmentInfoResponse createDepartmentInfoResponse(Long departmentId) {
        return DepartmentInfoResponse.builder()
                .departmentId(departmentId)
                .build();
    }
}