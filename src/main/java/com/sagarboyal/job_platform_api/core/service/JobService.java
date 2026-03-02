package com.sagarboyal.job_platform_api.core.service;

import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.core.dto.JobFilterInput;
import com.sagarboyal.job_platform_api.core.dto.JobSortInput;
import com.sagarboyal.job_platform_api.payload.PagedResponse;

import java.util.List;

public interface JobService {
    JobDto createJob(JobDto jobDto);
    List<JobDto> addBulkJobs(List<JobDto> bulkData);
    JobDto findJobById(Long id);
    PagedResponse<JobDto> findJobs(Integer page, Integer size);
    PagedResponse<JobDto> searchJobs(String search, JobFilterInput filter, Integer page, Integer size, JobSortInput sort);
}
