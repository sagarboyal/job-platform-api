package com.sagarboyal.job_platform_api.job.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

public enum EmploymentType {
    PERMANENT,
    CONTRACT,
    APPRENTICE,
    TEMPORARY,
}