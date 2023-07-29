package com.hr_program.api.service.jobHistory.response;

import com.hr_program.domain.jobHistory.JobHistory;
import lombok.Builder;

import java.util.Date;

@Builder
public record JobHistoryResponse (
    Date startDate,
    Date endDate,
    String jobId,
    String jobTitle,
    Long departmentId,
    String departmentName
){
    public static JobHistoryResponse from(JobHistory jobHistory) {
        return JobHistoryResponse.builder()
                .startDate(jobHistory.getStartDate())
                .endDate(jobHistory.getEndDate())
                .jobId(jobHistory.getJob().getJobId())
                .jobTitle(jobHistory.getJob().getJobTitle())
                .departmentId(jobHistory.getDepartment().getDepartmentId())
                .departmentName(jobHistory.getDepartment().getDepartmentName())
                .build();
    }
}