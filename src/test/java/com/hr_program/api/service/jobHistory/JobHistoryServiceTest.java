package com.hr_program.api.service.jobHistory;

import com.hr_program.api.service.jobHistory.response.JobHistoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class JobHistoryServiceTest {

    @Autowired
    private JobHistoryService jobHistoryService;

    @DisplayName("사원의 아이디를 통해 사원의 이력 정보를 조회한다.")
    @Test
    void findJobHistoryByEmployeeId() {
        // given
        Long employeeId = 101L;

        // when
        List<JobHistoryResponse> jobHistory = jobHistoryService.getJobHistory(employeeId);

        // then
        assertThat(jobHistory).hasSize(2)
                .extracting("startDate", "endDate", "jobId", "jobTitle", "departmentId", "departmentName")
                .containsExactlyInAnyOrder(
                        tuple(Timestamp.valueOf(LocalDateTime.of(1989, 9, 21, 0, 0)), Timestamp.valueOf(LocalDateTime.of(1993, 10, 27, 0, 0)), "AC_ACCOUNT", "Public Accountant", 110L, "Accounting"),
                        tuple(Timestamp.valueOf(LocalDateTime.of(1993, 10, 28, 0, 0)), Timestamp.valueOf(LocalDateTime.of(1997, 3, 15, 0, 0)), "AC_MGR", "Accounting Manager", 110L, "Accounting")
                );
    }
}