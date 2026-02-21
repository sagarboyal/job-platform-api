package com.sagarboyal.job_platform_api.scrapper.watcher;

import com.sagarboyal.job_platform_api.scrapper.payload.dtos.ProviderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultHtmlChangeDetectionStrategy implements ChangeDetectionStrategy {

    private final WatcherCache cache;

    @Override
    public boolean hasChanged(
            ProviderDTO provider,
            String contentSelector,
            String ignoreSelector
    ) throws IOException {

        Document doc = Jsoup.connect(provider.url())
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();

        // 1️⃣ Determine content container
        Element content;

        if (contentSelector != null && !contentSelector.isBlank()) {
            content = doc.selectFirst(contentSelector);
        } else {
            content = doc.body();
        }

        if (content == null) {
            log.warn("No content found for provider {}", provider.name());
            return false;
        }

        if (ignoreSelector != null && !ignoreSelector.isBlank()) {
            content.select(ignoreSelector).remove();
        }

        String normalized = content.text()
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase();

        String newHash = DigestUtils.md5DigestAsHex(
                normalized.getBytes(StandardCharsets.UTF_8)
        );

        String cacheKey = "watcher:" + provider.name();
        String oldHash = cache.get(cacheKey);

        if (newHash.equals(oldHash)) {
            return false;
        }

        cache.put(cacheKey, newHash);
        return true;
    }
}