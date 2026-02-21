package com.sagarboyal.job_platform_api.scrapper.service;

import com.sagarboyal.job_platform_api.scrapper.payload.dtos.ProviderDTO;

import java.util.List;

public interface ProviderService {
    ProviderDTO create(ProviderDTO providerDTO);
    ProviderDTO update(ProviderDTO providerDTO);
    List<ProviderDTO> addBulk(List<ProviderDTO> providerDTOList);
    void delete(Long id);
    void toggleStatus(Long id);
    List<ProviderDTO> getAll();
    ProviderDTO getByName(String name);
}
