package com.sagarboyal.job_platform_api.core.repository;

import com.sagarboyal.job_platform_api.core.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
