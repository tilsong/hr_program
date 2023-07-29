package com.hr_program.domain.jobHistory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JobHistoryRepositoryTest {

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @DisplayName("사원의 아이디를 통해 사원의 이력 정보를 조회한다.")
    @Test
    void findJobHistoryByEmployeeId() {
        // given
        Long employeeId = 101L;

        // when
        List<JobHistory> responses = jobHistoryRepository.findJobHistoryByEmployeeId(employeeId);

        // then
        assertThat(responses).hasSize(2);
    }
}