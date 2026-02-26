package com.sagarboyal.job_platform_api.scrapper.schedular;

import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.core.service.JobService;
import com.sagarboyal.job_platform_api.scrapper.modal.Provider;
import com.sagarboyal.job_platform_api.scrapper.providers.JobProvider;
import com.sagarboyal.job_platform_api.scrapper.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderSchedulerService {

    private final ThreadPoolTaskScheduler scheduler;
    private final ProviderRepository providerRepository;
    private final List<JobProvider> providers;
    private final JobService jobService;

    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    private final Set<Long> runningProviders = ConcurrentHashMap.newKeySet();

    /**
     * Schedule all active providers at startup
     */
    public void scheduleAllProviders() {

        for (JobProvider provider : providers) {

            providerRepository.findByName(provider.providerName())
                    .filter(Provider::getActive)
                    .ifPresent(data -> scheduleProvider(provider, data));
        }

        log.info("✅ Scheduled {} providers", scheduledTasks.size());
    }

    /**
     * Schedule provider execution
     */
    private void scheduleProvider(JobProvider provider, Provider data) {

        Runnable task = () -> executeProvider(provider);

        long delay = data.getFrequencyMinutes() * 60_000L;

        // jitter prevents burst traffic
        delay += ThreadLocalRandom.current().nextLong(2000, 8000);

        ScheduledFuture<?> future = scheduler.schedule(
                task,
                new Date(System.currentTimeMillis() + delay)
        );

        scheduledTasks.put(data.getId(), future);

        log.info("⏳ {} scheduled in {} sec", data.getName(), delay / 1000);
    }

    /**
     * Actual execution logic
     */
    private void executeProvider(JobProvider provider) {

        Provider data = providerRepository
                .findByName(provider.providerName())
                .orElse(null);

        if (data == null || !data.getActive()) {
            log.warn("Provider {} not active or missing", provider.providerName());
            return;
        }

        Long id = data.getId();
        String name = data.getName();

        if (!runningProviders.add(id)) {
            log.warn("Skipping {} — already running", name);
            return;
        }

        long start = System.currentTimeMillis();
        log.info("▶ Running {}", name);

        try {
            List<JobDto> jobs = provider.getJobLists();

            if (jobs == null || jobs.isEmpty()) {
                log.info("{} returned no jobs", name);
            } else {
                List<JobDto> saved = jobService.addBulkJobs(jobs);
                log.info("Saved {}/{} jobs from {}", saved.size(), jobs.size(), name);
            }

        } catch (Exception ex) {
            log.error("{} failed: {}", name, ex.getMessage(), ex);
        } finally {
            runningProviders.remove(id);
        }

        log.info("⏱ {} completed in {} ms", name, System.currentTimeMillis() - start);

        rescheduleProvider(provider, id);
    }

    /**
     * Reschedule using latest DB configuration
     */
    private void rescheduleProvider(JobProvider provider, Long providerId) {

        ScheduledFuture<?> existing = scheduledTasks.remove(providerId);
        if (existing != null) {
            existing.cancel(false);
        }

        providerRepository.findById(providerId)
                .filter(Provider::getActive)
                .ifPresent(updated -> scheduleProvider(provider, updated));
    }

    /**
     * Cancel provider scheduling
     */
    public void cancelProvider(Long providerId) {
        ScheduledFuture<?> existing = scheduledTasks.remove(providerId);
        if (existing != null) {
            existing.cancel(false);
        }
    }
}