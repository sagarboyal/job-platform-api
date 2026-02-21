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
            return changeDetectionStrategy.hasChanged(provider, contentSelector, ignoreSelector);
        } catch (IOException e) {
            log.error("Watcher failed for provider: {}", provider.name(), e);
            return false;
        }
    }
}