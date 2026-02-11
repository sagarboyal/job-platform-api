package com.sagarboyal.job_platform_api.aggregator.providers;

import java.io.IOException;
import java.util.List;

public interface JobProvider {
    List<?> getJobLists() throws IOException;
}
