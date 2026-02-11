package com.sagarboyal.job_platform_api.job.dto;

import com.sagarboyal.job_platform_api.job.entity.EmploymentType;
import com.sagarboyal.job_platform_api.job.entity.JobStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record JobDto(
    Long id,
    String advertisementNo,
    String title,
    String organization,
    String providerName,
    String jobLocation,
    String qualification,
    Integer totalVacancies,
    LocalDate startDate,
    LocalDate lastDate,
    String officialNotificationUrl,
    String sourceUrl,
    String description,
    JobStatus status,
    String category,
    EmploymentType employmentType,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
