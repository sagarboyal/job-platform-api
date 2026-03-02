package com.sagarboyal.job_platform_api.core.dto;

import com.sagarboyal.job_platform_api.core.entity.EmploymentType;
import com.sagarboyal.job_platform_api.core.entity.JobStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class JobFilterInput {
    private String providerName;
    private JobStatus status;
    private String category;
    private EmploymentType employmentType;
    private String location;
    private LocalDate postedFrom;
    private LocalDate postedTo;
    private LocalDateTime createdFrom;
    private LocalDateTime createdTo;
    private LocalDateTime updatedFrom;
    private LocalDateTime updatedTo;
}
