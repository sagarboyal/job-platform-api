package com.sagarboyal.job_platform_api.scrapper.providers.ncs;

import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.scrapper.config.JobProviderProperties;
import com.sagarboyal.job_platform_api.scrapper.payload.NCSResponse;
import com.sagarboyal.job_platform_api.scrapper.providers.JobProvider;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Primary
@RequiredArgsConstructor
public class NCSProvider implements JobProvider {
    private final JobProviderProperties properties;


    @Override
    public List<JobDto> getJobLists() throws IOException {

        String URL = properties
            .getProviders()
            .get("ncs")
            .getUrl();

        Document doc = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0")
                .get();

        Elements tables = doc.select("div#ABR\\ ministries_16978 table");
        Elements rows = tables.select("tbody tr");

        List<JobDto> result = new ArrayList<>();

        for (Element row : rows) {
            Elements cells = row.select("td");
            if(cells.size() != 5) continue;

            String serialNumber = cells.get(0).text();
            String ministry = cells.get(1).text();
            String department = cells.get(2).text();
            String homePage = cells.get(3).text();
            String recruitmentPage = cells.get(4).text();

            department = department.isBlank()
                    ? "No description available. Please refer to the official notification link below for complete details."
                    : department;

            NCSResponse response = NCSResponse.builder()
                    .id(serialNumber)
                    .ministry(ministry)
                    .department(department)
                    .homePage(homePage)
                    .recruitmentPage(recruitmentPage)
                    .providerUrl(URL)
                    .build();

            result.add(convertToJobDto(response));
        }
        return result;
    }

    @Override
    public String providerName() {
        return "ncs";
    }

    private JobDto convertToJobDto(NCSResponse ncs) {
        return JobDto.builder()
                .title(ncs.ministry())
                .description(ncs.department())
                .officialNotificationUrl(ncs.recruitmentPage())
                .sourceUrl(ncs.homePage())
                .providerUrl(ncs.providerUrl())
                .providerName("NCS")
                .build();
    }
}
