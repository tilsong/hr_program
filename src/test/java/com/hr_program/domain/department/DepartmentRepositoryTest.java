package com.hr_program.domain.department;

import com.hr_program.domain.department.exception.DepartmentNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @DisplayName("부서 아이디를 통해 부서 및 위치 정보를 조회한다.")
    @Test
    void findDepartmentAndLocationInfoByDepartmentId() {
        // given
        Long departmentId = 10L;

        // when
        Department department = departmentRepository.findDepartmentById(departmentId)
                .orElseThrow(null);

        // then
        assertThat(department.getDepartmentId()).isEqualTo(departmentId);
    }

    @DisplayName("존재하지 않는 부서 아이디를 통해 부서 및 위치 정보를 조회하면 실패한다.")
    @Test
    void findDepartmentAndLocationInfoByNoexistsDepartmentId() {
        // given
        Long departmentId = 0L;

        // when then
        assertThatThrownBy(() -> departmentRepository.findDepartmentById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException(departmentId)))
                .isInstanceOf(DepartmentNotFoundException.class)
                .hasMessage("존재하지 않는 부서 Id입니다.");
    }
}