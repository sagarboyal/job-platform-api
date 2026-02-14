package com.sagarboyal.job_platform_api.core.dto;

import com.sagarboyal.job_platform_api.core.entity.EmploymentType;
import com.sagarboyal.job_platform_api.core.entity.JobStatus;
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
    LocalDate postedDate,
    String officialNotificationUrl,
    String sourceUrl,
    String providerUrl,
    String description,
    JobStatus status,
    String category,
    EmploymentType employmentType,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
