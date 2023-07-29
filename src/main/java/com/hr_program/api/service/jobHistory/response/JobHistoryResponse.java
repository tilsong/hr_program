package com.hr_program.api.service.jobHistory.response;

import com.hr_program.domain.jobHistory.JobHistory;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class JobHistoryResponse {
    private Date startDate;
    private Date endDate;
    private String jobId;
    private String jobTitle;
    private Long departmentId;
    private String departmentName;

    @Builder
    public JobHistoryResponse(Date startDate, Date endDate, String jobId, String jobTitle, Long departmentId, String departmentName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public static JobHistoryResponse of(JobHistory jobHistory) {
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