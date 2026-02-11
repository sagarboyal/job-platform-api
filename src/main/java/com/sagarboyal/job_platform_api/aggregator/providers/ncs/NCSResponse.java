package com.sagarboyal.job_platform_api.aggregator.providers.ncs;

import lombok.Builder;

@Builder
public record NCSResponse(
        String id,
        String ministry,
        String department,
        String homePage,
        String recruitmentPage
) {
}
