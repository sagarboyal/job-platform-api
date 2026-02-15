package com.sagarboyal.job_platform_api.scrapper.payload.dtos;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProviderDTO(
        Long id,
        String name,
        String fullName,
        String url,
        Boolean active,
        Integer frequencyMinutes,
        LocalDateTime updateAt
) {
}
