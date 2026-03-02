package com.sagarboyal.job_platform_api.core.dto;

import lombok.Data;

@Data
public class JobSortInput {
    private JobSortField field;
    private SortDirection direction;
}
