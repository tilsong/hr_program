package com.hr_program.api.controller.department;

import com.hr_program.api.service.department.DepartmentService;
import com.hr_program.api.service.department.response.DepartmentInfoResponse;
import com.hr_program.domain.department.exception.DepartmentNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

    private static DepartmentInfoResponse createDepartmentInfoResponse(Long departmentId) {
        return DepartmentInfoResponse.builder()
                .departmentId(departmentId)
                .build();
    }
}