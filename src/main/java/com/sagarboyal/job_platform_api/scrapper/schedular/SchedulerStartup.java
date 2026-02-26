package com.sagarboyal.job_platform_api.scrapper.schedular;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerStartup implements ApplicationRunner {

    private final ProviderSchedulerService scheduler;

    @Override
    public void run(@NonNull ApplicationArguments args) {
        scheduler.scheduleAllProviders();
    }
}