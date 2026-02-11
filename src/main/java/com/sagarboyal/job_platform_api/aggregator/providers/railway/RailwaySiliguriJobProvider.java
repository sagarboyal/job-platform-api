package com.sagarboyal.job_platform_api.aggregator.providers.railway;


import com.sagarboyal.job_platform_api.aggregator.config.JobProviderProperties;
import com.sagarboyal.job_platform_api.aggregator.providers.JobProvider;
import com.sagarboyal.job_platform_api.aggregator.utils.StringUtils;
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
    private final JobProviderProperties properties;
    private final StringUtils stringUtils;

    @Override
    public List<?> getJobLists() throws IOException {
        String URL = properties.getProviders()
                .get("railway")
                .getRegions()
                .get("siliguri")
                .getUrl();

        Document doc = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0")
                .get();

        Elements elements = doc.select(".highlightNews");
        Elements subDivs = elements.select("div.subsecBlock");
        List<RailwayJobResponse> results = new ArrayList<>();
        for(Element subDiv : subDivs){
            Elements header = subDiv.select("div.subsecBlockHead");
            Elements body = subDiv.select("div.subsecBlockBody");

            Elements title = body.select("div.category");
            Elements description = body.select("u");

            System.out.println(title.text());
            Elements link = body.select("a[href]");
            String href = link.attr("href");
            if(href.endsWith(".pdf")) href = "https://www.rrbsiliguri.gov.in/english" + href.substring(1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            Elements publishDate = body.select("span");
            String datePart = publishDate.text().replaceAll("[^0-9\\-]", ""); // "29-08-2024"
            LocalDate date = LocalDate.parse(datePart, formatter);

            results.add(RailwayJobResponse.builder()
                    .jobId(stringUtils.cleanText(header.text()))
                    .title(stringUtils.cleanText(title.text()))
                    .description(stringUtils.cleanText(description.text()))
                    .postedDate(date.toString())
                    .link(href)
                    .build());

        }
        return results;
    }
}
