package com.hr_program.api.controller.jobHistory;

import com.hr_program.api.ApiResponse;
import com.hr_program.api.service.jobHistory.JobHistoryService;
import com.hr_program.api.service.jobHistory.response.JobHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class JobHistoryController {

    private final JobHistoryService jobHistoryService;

    @GetMapping("/api/employee/{employeeId}/history")
    public ApiResponse<List<JobHistoryResponse>> getEmployeeJobHistory(@PathVariable Long employeeId) {
        return ApiResponse.ok(jobHistoryService.getJobHistory(employeeId));
    }
}
