package com.hr_program.api.service.jobHistory;

import com.hr_program.api.service.jobHistory.response.JobHistoryResponse;
import com.hr_program.domain.jobHistory.JobHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class JobHistoryService {

    private final JobHistoryRepository jobHistoryRepository;

    public List<JobHistoryResponse> getJobHistory(Long employeeId) {
        return jobHistoryRepository.findJobHistoryByEmployeeId(employeeId)
                .stream()
                .map(JobHistoryResponse::from)
                .collect(Collectors.toList());
    }
}
