package com.sagarboyal.job_platform_api.scrapper.serviceImpl;

import com.sagarboyal.job_platform_api.scrapper.mapper.ProviderMapper;
import com.sagarboyal.job_platform_api.scrapper.modal.Provider;
import com.sagarboyal.job_platform_api.scrapper.payload.ProviderDTO;
import com.sagarboyal.job_platform_api.scrapper.repository.ProviderRepository;
import com.sagarboyal.job_platform_api.scrapper.service.ProviderService;
import com.sagarboyal.job_platform_api.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {
    private final ProviderRepository providerRepository;
    private final ProviderMapper providerMapper;

    @Override
    public ProviderDTO create(ProviderDTO providerDTO) {
        Provider provider = providerMapper.toEntity(providerDTO);
        return providerMapper.toResponse(providerRepository.save(provider));
    }

    @Override
    public ProviderDTO update(ProviderDTO dto) {
        if(dto.id() == null){
            throw new InvalidParameterException("id is null");
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
        data.setActive(!data.isActive());
        providerRepository.save(data);
    }

    @Override
    public List<ProviderDTO> getAll() {
        return providerRepository.findAll()
                .stream()
                .map(providerMapper::toResponse)
                .toList();
    }

    private Provider findById(Long id) {
        return providerRepository.findById(id)
                .orElseThrow( () -> new RuntimeException( "No provider with id " + id + " found." ) );
    }
}
