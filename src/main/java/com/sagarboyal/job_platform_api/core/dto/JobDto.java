package com.sagarboyal.job_platform_api.core.dto;

import com.sagarboyal.job_platform_api.core.entity.EmploymentType;
import com.sagarboyal.job_platform_api.core.entity.JobStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class JobDto{
    private Long id;
    private String advertisementNo;
    private String title;
    private String organization;
    private String providerName;
    private String jobLocation;
    private String qualification;
    private Integer totalVacancies;
    private LocalDate startDate;
    private LocalDate lastDate;
    private LocalDate postedDate;
    private String officialNotificationUrl;
    private String sourceUrl;
    private String providerUrl;
    private String description;
    private JobStatus status;
    private String category;
    private EmploymentType employmentType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
