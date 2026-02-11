package com.sagarboyal.job_platform_api.core.service;

import com.sagarboyal.job_platform_api.core.dto.JobDto;

public interface JobService {
    JobDto createJob(JobDto jobDto);
}
