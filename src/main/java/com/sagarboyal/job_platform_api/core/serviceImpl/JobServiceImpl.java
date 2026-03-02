package com.sagarboyal.job_platform_api.core.serviceImpl;

import com.sagarboyal.job_platform_api.constants.AppConstants;
import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.payload.PagedResponse;
import com.sagarboyal.job_platform_api.core.entity.Job;
import com.sagarboyal.job_platform_api.core.mapper.JobMapper;
import com.sagarboyal.job_platform_api.core.repository.JobRepository;
import com.sagarboyal.job_platform_api.core.service.JobCodeGenerator;
import com.sagarboyal.job_platform_api.core.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    @Override
    public List<JobDto> addBulkJobs(List<JobDto> bulkData) {
        List<JobDto> savedJobs = new ArrayList<>();
        for (JobDto jobDto : bulkData) {
            try {
                Job job = jobMapper.toEntity(jobDto);
                savedJobs.add(jobMapper.toResponse(jobRepository.save(job)));
            } catch (DataIntegrityViolationException ex) {
                log.warn("Skipping duplicate job: {} from provider: {}",
                        jobDto.getTitle(), jobDto.getProviderName());
            }
        }
        return savedJobs;
    }

    @Override
    public JobDto findJobById(Long id) {
        return jobMapper.toResponse(jobRepository.findById(id).orElse(null));
    }

    @Override
    public PagedResponse<JobDto> findJobs(Integer page, Integer size) {
        page = page == null ? AppConstants.DEFAULT_PAGE_NUMBER : page;
        size = size == null ? AppConstants.DEFAULT_PAGE_SIZE : size;

        Page<Job> jobPage = jobRepository.findAll(PageRequest.of(page, size));
        List<JobDto> content = jobPage.getContent().stream().map(jobMapper::toResponse).toList();

        return PagedResponse.<JobDto>builder()
                .content(content)
                .page(jobPage.getNumber())
                .size(jobPage.getSize())
                .totalElements(Math.toIntExact(jobPage.getTotalElements()))
                .totalPages(jobPage.getTotalPages())
                .hasNext(jobPage.hasNext())
                .hasPrevious(jobPage.hasPrevious())
                .build();
    }
}
