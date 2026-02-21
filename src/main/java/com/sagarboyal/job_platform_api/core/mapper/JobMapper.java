package com.sagarboyal.job_platform_api.core.mapper;

import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.core.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public Job toEntity(JobDto jobDto){

        if (jobDto == null) return null;

        Job job = new Job();
        job.setId(jobDto.getId());
        job.setAdvertisementNo(jobDto.getAdvertisementNo());
        job.setTitle(jobDto.getTitle());
        job.setOrganization(jobDto.getOrganization());
        job.setProviderName(jobDto.getProviderName());
        job.setJobLocation(jobDto.getJobLocation());
        job.setQualification(jobDto.getQualification());
        job.setTotalVacancies(jobDto.getTotalVacancies());
        job.setStartDate(jobDto.getStartDate());
        job.setLastDate(jobDto.getLastDate());
        job.setPostedDate(jobDto.getPostedDate());

        job.setOfficialNotificationUrl(jobDto.getOfficialNotificationUrl());
        job.setSourceUrl(jobDto.getSourceUrl());
        job.setProviderUrl(jobDto.getProviderUrl());

        job.setDescription(jobDto.getDescription());
        job.setStatus(jobDto.getStatus());
        job.setCategory(jobDto.getCategory());
        job.setEmploymentType(jobDto.getEmploymentType());

        job.setCreatedAt(jobDto.getCreatedAt());
        job.setUpdatedAt(jobDto.getUpdatedAt());

        return job;
    }

    public JobDto toResponse(Job job) {

        if (job == null) return null;

        return JobDto.builder()
                .id(job.getId())
                .advertisementNo(job.getAdvertisementNo())
                .title(job.getTitle())
                .organization(job.getOrganization())
                .providerName(job.getProviderName())
                .jobLocation(job.getJobLocation())
                .qualification(job.getQualification())
                .totalVacancies(job.getTotalVacancies())
                .startDate(job.getStartDate())
                .lastDate(job.getLastDate())
                .postedDate(job.getPostedDate())
                .officialNotificationUrl(job.getOfficialNotificationUrl())
                .sourceUrl(job.getSourceUrl())
                .providerUrl(job.getProviderUrl())
                .description(job.getDescription())
                .status(job.getStatus())
                .category(job.getCategory())
                .employmentType(job.getEmploymentType())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .build();
    }

}
