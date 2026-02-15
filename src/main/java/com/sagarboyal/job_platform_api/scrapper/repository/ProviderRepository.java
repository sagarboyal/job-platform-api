package com.sagarboyal.job_platform_api.scrapper.repository;

import com.sagarboyal.job_platform_api.scrapper.modal.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    boolean existsByNameAndUrl(String name, String url);
    Optional<Provider> findByName(String name);
}
