package com.sagarboyal.job_platform_api.core.repository;

import com.sagarboyal.job_platform_api.core.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsByProviderNameAndAdvertisementNo(String providerName, String base);

    @Query("SELECT j.advertisementNo FROM Job j WHERE j.advertisementNo IN :keys")
    List<String> findExistingAdvertisementNos(Collection<String> keys);
}
