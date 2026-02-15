package com.sagarboyal.job_platform_api.scrapper.providers.ncs;

import com.sagarboyal.job_platform_api.core.dto.JobDto;
import com.sagarboyal.job_platform_api.scrapper.payload.dtos.ProviderDTO;
import com.sagarboyal.job_platform_api.scrapper.payload.response.NCSResponse;
import com.sagarboyal.job_platform_api.scrapper.providers.JobProvider;
import com.sagarboyal.job_platform_api.scrapper.service.ProviderService;
import com.sagarboyal.job_platform_api.utils.StringUtils;
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
    private final StringUtils stringUtils;
    private final ProviderService providerService;

    @Override
    public List<JobDto> getJobLists() throws IOException {
        ProviderDTO providerDTO = providerService.getByName("NCS");
        String URL = providerDTO.url();

        if(!providerDTO.active()) return null;

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

            NCSResponse response = NCSResponse.builder()
                    .id(serialNumber)
                    .ministry(ministry)
                    .department(stringUtils.cleanDescription(department))
                    .homePage(stringUtils.cleanURL(homePage))
                    .recruitmentPage(stringUtils.cleanURL(recruitmentPage))
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
