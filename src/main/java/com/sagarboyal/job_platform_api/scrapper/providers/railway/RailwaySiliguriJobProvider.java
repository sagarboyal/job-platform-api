package com.sagarboyal.job_platform_api.scrapper.providers.railway;


import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.scrapper.payload.dtos.ProviderDTO;
import com.sagarboyal.job_platform_api.scrapper.payload.response.RailwayJobResponse;
import com.sagarboyal.job_platform_api.scrapper.providers.JobProvider;
import com.sagarboyal.job_platform_api.scrapper.service.ProviderService;
import com.sagarboyal.job_platform_api.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service("RailwaySiliguriJobProvider")
@RequiredArgsConstructor
public class RailwaySiliguriJobProvider implements JobProvider {
    private final StringUtils stringUtils;
    private final ProviderService providerService;

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
        List<JobDto> results = new ArrayList<>();
        for(Element subDiv : subDivs){
            Elements header = subDiv.select("div.subsecBlockHead");
            Elements body = subDiv.select("div.subsecBlockBody");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            Elements publishDate = body.select("span");
            String datePart = publishDate.text().replaceAll("[^0-9\\-]", ""); // "29-08-2024"
            LocalDate date = LocalDate.parse(datePart, formatter);

            LocalDate cutoff = LocalDate.of(2026, 1, 1);
            if (date.isBefore(cutoff)) break;

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

            results.add(convertToJobDto(response));
        }
        return results;
    }

    @Override
    public String providerName() {
        return "railway-siliguri";
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
