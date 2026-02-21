package com.sagarboyal.job_platform_api.scrapper.watcher;

import com.sagarboyal.job_platform_api.scrapper.payload.dtos.ProviderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class WatcherService {

    private final ChangeDetectionStrategy changeDetectionStrategy;

    public boolean hasChanged(ProviderDTO provider, String contentSelector,
                              String ignoreSelector) {

        try {
            boolean changed = changeDetectionStrategy.hasChanged(provider, contentSelector, ignoreSelector);
            if (changed) {
                log.info("Change detected for provider: {}", provider.name());
            } else {
                log.debug("No change for provider: {}", provider.name());
            }
            return changed;

        } catch (IOException e) {
            log.error("Watcher failed for provider: {}", provider.name(), e);
            return false;
        }
    }
}