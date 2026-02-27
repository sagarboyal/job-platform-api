package com.sagarboyal.job_platform_api.core.graphql;

import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.core.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQLController {
    private final JobService jobService;

    @QueryMapping
    public JobDto getJob(@Argument Long id) {
        return jobService.findJobById(id);
    }

    @QueryMapping
    public List<JobDto> getAllJob() {
        return jobService.findAllJobs();
    }
}
