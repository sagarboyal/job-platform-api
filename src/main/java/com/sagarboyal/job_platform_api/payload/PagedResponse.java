package com.sagarboyal.job_platform_api.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PagedResponse <T> {
    private List<T> content;
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
