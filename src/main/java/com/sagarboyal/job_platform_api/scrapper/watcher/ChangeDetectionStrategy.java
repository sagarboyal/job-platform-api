package com.sagarboyal.job_platform_api.scrapper.watcher;

import com.sagarboyal.job_platform_api.scrapper.payload.dtos.ProviderDTO;

import java.io.IOException;

public interface ChangeDetectionStrategy {
    boolean hasChanged(ProviderDTO provider,  String contentSelector,
                       String ignoreSelector) throws IOException;
}