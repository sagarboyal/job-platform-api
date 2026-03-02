package com.sagarboyal.job_platform_api.core.graphql;

import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.core.dto.JobFilterInput;
import com.sagarboyal.job_platform_api.core.dto.JobSortInput;
import com.sagarboyal.job_platform_api.payload.PagedResponse;
import com.sagarboyal.job_platform_api.core.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GraphQLController {
    private final JobService jobService;

    @QueryMapping
    public JobDto getJob(@Argument Long id) {
        return jobService.findJobById(id);
    }

    @QueryMapping
    public PagedResponse<JobDto> getAllJob(@Argument Integer page, @Argument Integer size) {
        return jobService.findJobs(page, size);
    }

    @QueryMapping
    public PagedResponse<JobDto> searchJobs(@Argument String search,
                                            @Argument JobFilterInput filter,
                                            @Argument Integer page,
                                            @Argument Integer size,
                                            @Argument JobSortInput sort) {
        return jobService.searchJobs(search, filter, page, size, sort);
    }
}
