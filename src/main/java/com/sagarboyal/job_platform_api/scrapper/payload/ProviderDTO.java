package com.sagarboyal.job_platform_api.scrapper.payload;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
public record ProviderDTO(
        Long id,
        String name,
        String fullName,
        String url,
        boolean active,
        Integer frequencyMinutes,
        LocalDateTime updateAt
) {
}
