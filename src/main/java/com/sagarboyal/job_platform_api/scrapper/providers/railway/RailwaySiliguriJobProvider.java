package com.sagarboyal.job_platform_api.scrapper.providers.railway;


import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.core.repository.JobRepository;
import com.sagarboyal.job_platform_api.core.service.JobCodeGenerator;
import com.sagarboyal.job_platform_api.scrapper.payload.dtos.ProviderDTO;
import com.sagarboyal.job_platform_api.scrapper.payload.response.RailwayJobResponse;
import com.sagarboyal.job_platform_api.scrapper.providers.JobProvider;
import com.sagarboyal.job_platform_api.scrapper.service.ProviderService;
import com.sagarboyal.job_platform_api.scrapper.watcher.WatcherService;
import com.sagarboyal.job_platform_api.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service("RailwaySiliguriJobProvider")
@RequiredArgsConstructor
public class RailwaySiliguriJobProvider implements JobProvider {
    private final StringUtils stringUtils;
    private final ProviderService providerService;
    private final WatcherService watcherService;
    private final JobCodeGenerator jobCodeGenerator;
    private final JobRepository jobRepository;

    @Override
    public List<JobDto> getJobLists() throws IOException {

        ProviderDTO providerDTO = providerService.getByName("RAILWAY_SILIGURI");
        String URL = providerDTO.url();

        if(!providerDTO.active()) return null;

        Document doc = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0")
                .get();

        Elements elements = doc.select(".highlightNews");
        Elements subDivs = elements.select("div.subsecBlock");

        if (!watcherService.hasChanged(providerDTO,
                ".highlightNews",
                null)) {
            log.info("No change → skipping scrape railway siliguri jobs");
            return Collections.emptyList();
        }

        List<String> scrapedKeys = new ArrayList<>();
        Map<String, JobDto> jobMap = new HashMap<>();
        Set<String> usedCodesInBatch = new HashSet<>();

        for(Element subDiv : subDivs){
            JobDto dto = buildResponse(subDiv, URL);
            String code = jobCodeGenerator.generate(dto, usedCodesInBatch);

            dto.setAdvertisementNo(code);
            scrapedKeys.add(code);
            jobMap.put(code, dto);
            usedCodesInBatch.add(code);
        }

        List<JobDto> newJobs = new ArrayList<>();
        Set<String> existingKeys =
                new HashSet<>(jobRepository.findExistingAdvertisementNos(scrapedKeys));
        for(String key : scrapedKeys){
            if(existingKeys.contains(key)) continue;
            newJobs.add(jobMap.get(key));
        }

        return newJobs;
    }

    @Override
    public String providerName() {
        return "RAILWAY-SILIGURI";
    }

    private JobDto buildResponse(Element subDiv, String URL) {
        Elements header = subDiv.select("div.subsecBlockHead");
        Elements body = subDiv.select("div.subsecBlockBody");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Elements publishDate = body.select("span");
        String datePart = publishDate.text().replaceAll("[^0-9\\-]", ""); // "29-08-2024"
        LocalDate date = LocalDate.parse(datePart, formatter);

        Elements title = body.select("div.category");
        Elements description = body.select("u");

        Elements link = body.select("a[href]");
        String href = link.attr("href");
        if(href.endsWith(".pdf")) href = "https://www.rrbsiliguri.gov.in/english" + href.substring(1);

        String descText = stringUtils.cleanText(description.text());
        String jobId = stringUtils.cleanText(header.text());

        String descriptionText = descText.isBlank()
                ? "Job ID: " + jobId
                : descText + "\nJob ID: " + jobId;

        RailwayJobResponse response = RailwayJobResponse.builder()
                .jobId(stringUtils.cleanText(header.text()))
                .title(stringUtils.cleanText(title.text()))
                .description(descriptionText)
                .postedDate(date.toString())
                .link(href)
                .providerUrl(URL)
                .build();

        return convertToJobDto(response);
    }

    private JobDto convertToJobDto(RailwayJobResponse response) {
        return JobDto.builder()
                .title(response.title())
                .description(response.description())
                .officialNotificationUrl(response.link())
                .providerUrl(response.providerUrl())
                .providerName("RAILWAY-SILIGURI")
                .postedDate(LocalDate.parse(response.postedDate()))
                .build();
    }
}
