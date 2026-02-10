package com.sagarboyal.job_platform_api.job.mapper;

import com.sagarboyal.job_platform_api.job.dto.JobDto;
import com.sagarboyal.job_platform_api.job.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public Job toEntity(JobDto jobDto){

        if (jobDto == null) return null;

        Job job = new Job();
        job.setId(jobDto.id());
        job.setAdvertisementNo(jobDto.advertisementNo());
        job.setTitle(jobDto.title());
        job.setOrganization(jobDto.organization());
        job.setProviderName(jobDto.providerName());
        job.setJobLocation(jobDto.jobLocation());
        job.setQualification(jobDto.qualification());
        job.setTotalVacancies(jobDto.totalVacancies());
        job.setStartDate(jobDto.startDate());
        job.setLastDate(jobDto.lastDate());

        job.setOfficialNotificationUrl(jobDto.officialNotificationUrl());
        job.setSourceUrl(jobDto.sourceUrl());

        job.setDescription(jobDto.description());
        job.setStatus(jobDto.status());
        job.setCategory(jobDto.category());
        job.setEmploymentType(jobDto.employmentType());

        job.setCreatedAt(jobDto.createdAt());
        job.setUpdatedAt(jobDto.updatedAt());

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
                .officialNotificationUrl(job.getOfficialNotificationUrl())
                .sourceUrl(job.getSourceUrl())
                .description(job.getDescription())
                .status(job.getStatus())
                .category(job.getCategory())
                .employmentType(job.getEmploymentType())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .build();
    }

}
