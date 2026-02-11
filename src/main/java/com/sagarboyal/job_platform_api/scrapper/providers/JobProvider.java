package com.sagarboyal.job_platform_api.scrapper.providers;

import java.io.IOException;
import java.util.List;

public interface JobProvider {
    List<?> getJobLists() throws IOException;
}
