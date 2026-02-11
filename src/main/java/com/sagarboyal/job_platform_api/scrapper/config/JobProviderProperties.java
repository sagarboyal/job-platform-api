package com.sagarboyal.job_platform_api.scrapper.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "app.job-providers")
@Getter
@Setter
public class JobProviderProperties {

    private Map<String, Provider> providers = new HashMap<>();

    @Getter
    @Setter
    public static class Provider {
        private boolean enabled;
        private String url;
        private Map<String, Region> regions = new HashMap<>();
    }

    @Getter
    @Setter
    public static class Region {
        private boolean enabled;
        private String url;
    }
}