package com.sagarboyal.job_platform_api.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String message,
        String errorCode,
        int status,
        String timestamp,
        String path
) {}
