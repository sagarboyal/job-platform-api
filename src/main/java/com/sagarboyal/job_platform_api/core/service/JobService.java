package com.sagarboyal.job_platform_api.core.service;

import com.sagarboyal.job_platform_api.core.dto.JobDto;

import java.util.List;

public interface JobService {
    JobDto createJob(JobDto jobDto);
    List<JobDto> addBulkJobs(List<JobDto> bulkData);
}
