package com.sagarboyal.job_platform_api.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs",
        indexes = {
                @Index(name = "idx_advertisement_no", columnList = "advertisement_no"),
                @Index(name = "idx_jobs_provider", columnList = "provider_name"),
                @Index(name = "idx_jobs_last_date", columnList = "last_date"),
                @Index(name = "idx_jobs_status", columnList = "status")
        },
        uniqueConstraints = @UniqueConstraint(
                name = "uk_provider_ad_no",
                columnNames = {"provider_name", "advertisement_no"}
        ))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String advertisementNo;

    @Column(nullable = false, length = 500)
    private String title;

    private String organization;

    @Column(name = "provider_name", nullable = false)
    private String providerName;

    private String jobLocation;

    private String qualification;

    private Integer totalVacancies;

    private LocalDate startDate;
    private LocalDate lastDate;
    private LocalDate postedDate;

    @Column(length = 1000)
    private String officialNotificationUrl;
    @Column(length = 1000)
    private String sourceUrl;
    @Column(nullable = false, length = 1000)
    private String providerUrl;

    @Column(length = 3000)
    private String description;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private String category;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = JobStatus.ACTIVE;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

