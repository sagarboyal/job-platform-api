package com.sagarboyal.job_platform_api.core.serviceImpl;

import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.core.service.JobProcessingService;
import com.sagarboyal.job_platform_api.core.service.JobService;
import com.sagarboyal.job_platform_api.scrapper.providers.JobProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobProcessingServiceImpl implements JobProcessingService {
    private final List<JobProvider> providers;
    private final JobService jobService;

    @Override
    public void processJobs() {
        for (JobProvider provider : providers) {
            long startTime = System.currentTimeMillis();
            String providerName = provider.providerName();

            log.info("▶ Starting provider: {}", providerName);
            try {
                List<JobDto> jobs = provider.getJobLists();
                log.info("Fetched {} jobs from provider {}", jobs.size(), providerName);

                List<JobDto> saved = jobService.addBulkJobs(jobs);
                log.info("Successfully saved {}/{} jobs from provider {}",
                        saved.size(), jobs.size(), providerName);
            } catch (Exception ex) {
                log.error("Provider {} failed: {}", providerName, ex.getMessage(), ex);
            }

            long duration = System.currentTimeMillis() - startTime;
            log.info("⏱ Provider {} completed in {} ms", providerName, duration);
        }
    }
}
