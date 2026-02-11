package com.sagarboyal.job_platform_api.job.service;

import com.sagarboyal.job_platform_api.job.dto.JobDto;

public interface JobService {
    JobDto createJob(JobDto jobDto);
}
