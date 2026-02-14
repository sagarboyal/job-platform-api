package com.sagarboyal.job_platform_api.scrapper.mapper;

import com.sagarboyal.job_platform_api.scrapper.modal.Provider;
import com.sagarboyal.job_platform_api.scrapper.payload.ProviderDTO;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapper {

    public Provider toEntity(ProviderDTO dto) {
        Provider provider = new Provider();
        provider.setId(dto.id());
        provider.setName(dto.name());
        provider.setFullName(dto.fullName());
        provider.setUrl(dto.url());
        provider.setActive(dto.active());
        provider.setFrequencyMinutes(dto.frequencyMinutes());
        provider.setUpdatedAt(dto.updateAt());
        return provider;
    }

    public ProviderDTO toResponse(Provider provider) {
        return ProviderDTO.builder()
                .id(provider.getId())
                .name(provider.getName())
                .fullName(provider.getFullName())
                .url(provider.getUrl())
                .active(provider.isActive())
                .frequencyMinutes(provider.getFrequencyMinutes())
                .updateAt(provider.getUpdatedAt())
                .build();
    }
}
