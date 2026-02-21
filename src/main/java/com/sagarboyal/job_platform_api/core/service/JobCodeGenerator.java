package com.sagarboyal.job_platform_api.core.service;

import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.core.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JobCodeGenerator {

    private final JobRepository jobRepository;

    public String generate(JobDto dto, Set<String> usedInBatch) {
        String provider = safe(dto.getProviderName());

        if (hasText(dto.getAdvertisementNo())) {
            String code = provider + "-" + clean(dto.getProviderName());
            return ensureUnique(dto.getProviderName(), code, usedInBatch);
        }

        if (hasText(dto.getSourceUrl())) {
            String code = provider + "-" + shortHash(dto.getSourceUrl());
            return ensureUnique(dto.getProviderName(), code, usedInBatch);
        }

        String fingerprintData =
                safe(dto.getTitle()) +
                        safe(dto.getOrganization()) +
                        safe(dto.getJobLocation()) +
                        safe(dto.getCategory()) +
                        (dto.getLastDate() != null ? dto.getLastDate().toString() : "");

        return ensureUnique(dto.getProviderName(), provider + "-" + shortHash(fingerprintData), usedInBatch);
    }

    public String generate(JobDto dto) {
        return generate(dto, new HashSet<>());
    }

    private String ensureUnique(String providerName, String base, Set<String> usedInBatch) {
        String code = base;
        int counter = 1;

        while (
                jobRepository.existsByProviderNameAndAdvertisementNo(providerName, code)
                        || usedInBatch.contains(code)
        ) {
            code = base + counter++;
        }

        return code;
    }

    private String shortHash(String input) {
        return DigestUtils.md5DigestAsHex(input.getBytes()).substring(0, 12);
    }

    private boolean hasText(String s) {
        return s != null && !s.isBlank();
    }

    private String safe(String value) {
        if (value == null) return "";
        return value.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
    }

    private String clean(String value) {
        return value.replaceAll("\\s+", "").toUpperCase();
    }
}