package com.sagarboyal.job_platform_api.scrapper.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

    @Column(nullable=false, unique=true)
    private String fullName;

    @Column(nullable=false, length = 1000)
    private String url;

    private Boolean active;

    private Integer frequencyMinutes;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        active = true;
        frequencyMinutes = 60;
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
