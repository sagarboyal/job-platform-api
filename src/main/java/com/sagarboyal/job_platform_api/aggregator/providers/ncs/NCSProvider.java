package com.sagarboyal.job_platform_api.aggregator.providers.ncs;

import com.sagarboyal.job_platform_api.aggregator.providers.JobProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class NCSProvider implements JobProvider {

    @Override
    public List<NCSResponse> getJobLists() throws IOException {
        Document doc = Jsoup.connect("https://www.ncs.gov.in/pages/govt-job-vacancies.aspx")
                .userAgent("Mozilla/5.0")
                .get();

        Elements tables = doc.select("div#ABR\\ ministries_16978 table");
        Elements rows = tables.select("tbody tr");

        List<NCSResponse> result = new ArrayList<>();

        for (Element row : rows) {
            Elements cells = row.select("td");
            if(cells.size() != 5) continue;

            String serialNumber = cells.get(0).text();
            String ministry = cells.get(1).text();
            String department = cells.get(2).text();
            String homePage = cells.get(3).text();
            String recruitmentPage = cells.get(4).text();
            result.add(NCSResponse.builder()
                    .id(serialNumber)
                    .ministry(ministry)
                    .department(department)
                    .homePage(homePage)
                    .recruitmentPage(recruitmentPage)
                    .build());
        }
        return result;
    }
}
