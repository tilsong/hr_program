package com.hr_program.api.service.department;

import com.hr_program.api.service.department.response.DepartmentInfoResponse;
import com.hr_program.domain.department.exception.DepartmentNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @DisplayName("부서 아이디를 통해 부서 및 위치 정보를 조회한다.")
    @Test
    void findDepartmentAndLocationInfoByDepartmentId() {
        // given
        Long departmentId = 10L;
        Long mappedLocationId = 1700L;
        Long mappedManagerId = 200L;

        // when
        DepartmentInfoResponse departmentInfo = departmentService.getDepartmentInfo(departmentId);

        // then
        assertThat(departmentInfo.departmentId()).isEqualTo(departmentId);
        assertThat(departmentInfo.location().id()).isEqualTo(mappedLocationId);
        assertThat(departmentInfo.manager().id()).isEqualTo(mappedManagerId);
    }

    @DisplayName("존재하지 않는 부서 아이디를 통해 부서 및 위치 정보를 조회하면 실패한다.")
    @Test
    void findDepartmentAndLocationInfoByNoexistsDepartmentId() {
        // given
        Long departmentId = 0L;

        // when then
        assertThatThrownBy(() -> departmentService.getDepartmentInfo(departmentId))
                .isInstanceOf(DepartmentNotFoundException.class)
                .hasMessage("존재하지 않는 부서 Id입니다.");
    }

}