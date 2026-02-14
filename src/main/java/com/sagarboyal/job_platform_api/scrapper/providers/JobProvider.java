package com.sagarboyal.job_platform_api.scrapper.providers;

import com.sagarboyal.job_platform_api.core.dto.JobDto;

import java.io.IOException;
import java.util.List;

public interface JobProvider {
    List<JobDto> getJobLists() throws IOException;
    String providerName();
}
