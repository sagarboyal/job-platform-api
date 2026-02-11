package com.sagarboyal.job_platform_api.scrapper.payload;

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
