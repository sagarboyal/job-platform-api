package com.sagarboyal.job_platform_api.scrapper.serviceImpl;

import com.sagarboyal.job_platform_api.exception.custom.APIExceptions;
import com.sagarboyal.job_platform_api.scrapper.mapper.ProviderMapper;
import com.sagarboyal.job_platform_api.scrapper.modal.Provider;
import com.sagarboyal.job_platform_api.scrapper.payload.dtos.ProviderDTO;
import com.sagarboyal.job_platform_api.scrapper.repository.ProviderRepository;
import com.sagarboyal.job_platform_api.scrapper.service.ProviderService;
import com.sagarboyal.job_platform_api.utils.AppUtils;
import com.sagarboyal.job_platform_api.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {
    private final ProviderRepository providerRepository;
    private final ProviderMapper providerMapper;
    private final StringUtils stringUtils;

    @Override
    public ProviderDTO create(ProviderDTO providerDTO) {
        if (stringUtils.isBlank(providerDTO.name())) {
            throw new APIExceptions("Provider name is required");
        }

        if (stringUtils.isBlank(providerDTO.fullName())) {
            throw new APIExceptions("Provider full name is required");
        }

        if (stringUtils.isBlank(providerDTO.url())) {
            throw new APIExceptions("Provider URL is required");
        }

        if(providerRepository.existsByNameAndUrl(providerDTO.name(), providerDTO.url())) {
            throw new APIExceptions("Provider already exists");
        }

        Provider provider = providerMapper.toEntity(providerDTO);
        return providerMapper.toResponse(providerRepository.save(provider));
    }

    @Override
    public ProviderDTO update(ProviderDTO dto) {
        if(dto.id() == null){
            throw new APIExceptions("id is required");
        }

        Provider provider = findById(dto.id());
        provider.setName(AppUtils
                .keepOldIfUnchanged(
                        provider.getName(),
                        dto.name())
        );
        provider.setFullName(AppUtils
                .keepOldIfUnchanged(
                        provider.getFullName(),
                        dto.fullName())
        );
        provider.setDescription(
                AppUtils.keepOldIfUnchanged(
                        provider.getDescription(),
                        dto.description()
                )
        );
        provider.setUrl(AppUtils
                .keepOldIfUnchanged(
                        provider.getUrl(),
                        dto.url())
        );
        provider.setFrequencyMinutes(AppUtils
                .keepOldIfUnchanged(
                        provider.getFrequencyMinutes(),
                        dto.frequencyMinutes())
        );

        return providerMapper.toResponse(providerRepository.save(provider));
    }

    @Override
    public List<ProviderDTO> addBulk(List<ProviderDTO> dtoList) {
        List<Provider> providers = dtoList.stream()
                .map(providerMapper::toEntity)
                .toList();

        providers = providerRepository.saveAll(providers);
        return providers.stream()
                .map(providerMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Provider data = findById(id);
        providerRepository.delete(data);
    }

    @Override
    public void toggleStatus(Long id) {
        Provider data = findById(id);
        data.setActive(!data.getActive());
        providerRepository.save(data);
    }

    @Override
    public List<ProviderDTO> getAll() {
        return providerRepository.findAll()
                .stream()
                .map(providerMapper::toResponse)
                .toList();
    }

    @Override
    public ProviderDTO getByName(String name) {
        return providerMapper.toResponse(
                providerRepository.findByName(name)
                        .orElseThrow(() -> new APIExceptions("Provider not found"))
                );
    }

    private Provider findById(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new APIExceptions( "No provider with id " + id + " found.", HttpStatus.NOT_FOUND) );
    }
}
