package com.sagarboyal.job_platform_api.config;

import com.sagarboyal.job_platform_api.scrapper.providers.JobProvider;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StartupRunner implements CommandLineRunner {
    private final JobProvider provider;

    public StartupRunner(
            @Qualifier("RailwaySiliguriJobProvider") JobProvider provider) {
        this.provider = provider;
    }

    @Override
    public void run(String @NonNull ... args) throws IOException {
        System.out.println("Starting Job Platform API____________ ");
        provider.getJobLists()
                .forEach(System.out::println);
    }
}
