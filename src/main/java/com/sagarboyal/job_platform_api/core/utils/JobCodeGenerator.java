package com.sagarboyal.job_platform_api.core.utils;

import com.sagarboyal.job_platform_api.core.dto.JobDto;
import org.springframework.stereotype.Component;

@Component
public class JobCodeGenerator {

    public String generate(JobDto dto) {

        String provider = safe(dto.providerName());
        String org = safe(dto.organization());
        String year = dto.lastDate() != null
                ? String.valueOf(dto.lastDate().getYear())
                : "NA";

        if (dto.advertisementNo() != null && !dto.advertisementNo().isBlank()) {
            return provider + "-" + dto.advertisementNo().replaceAll("\\s+", "");
        }

        String titleWord = dto.title() != null
                ? dto.title().split(" ")[0]
                : "JOB";

        return provider + "-" + org + "-" + titleWord + "-" + year;
    }

    private String safe(String value) {
        if (value == null) return "NA";
        return value.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
    }
}
