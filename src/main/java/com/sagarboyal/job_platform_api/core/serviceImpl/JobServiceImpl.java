package com.sagarboyal.job_platform_api.core.serviceImpl;

import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.core.entity.Job;
import com.sagarboyal.job_platform_api.core.mapper.JobMapper;
import com.sagarboyal.job_platform_api.core.repository.JobRepository;
import com.sagarboyal.job_platform_api.core.service.JobService;
import com.sagarboyal.job_platform_api.core.utils.JobCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final JobCodeGenerator jobCodeGenerator;

    @Override
    public JobDto createJob(JobDto jobDto) {
        Job job = jobMapper.toEntity(jobDto);
        job.setAdvertisementNo(jobCodeGenerator.generate(jobDto));
        return jobMapper.toResponse(jobRepository.save(job));
    }
}
