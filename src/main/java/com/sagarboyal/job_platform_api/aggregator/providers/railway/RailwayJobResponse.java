package com.sagarboyal.job_platform_api.aggregator.providers.railway;

import lombok.Builder;

@Builder
public record RailwayJobResponse(
        String jobId,
        String title,
        String description,
        String link,
        String postedDate
) {
}
