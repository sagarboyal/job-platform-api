CREATE TABLE IF NOT EXISTS jobs (
    id                        BIGSERIAL PRIMARY KEY,
    advertisement_no          VARCHAR(255) NOT NULL,
    title                     VARCHAR(500) NOT NULL,
    organization              VARCHAR(255),
    provider_name             VARCHAR(255) NOT NULL,
    job_location              VARCHAR(255),
    qualification             VARCHAR(255),
    total_vacancies           INTEGER,
    start_date                DATE,
    last_date                 DATE,
    posted_date               DATE,
    official_notification_url VARCHAR(1000),
    source_url                VARCHAR(1000),
    provider_url              VARCHAR(1000) NOT NULL,
    description               VARCHAR(3000),
    status                    VARCHAR(50),
    category                  VARCHAR(255),
    employment_type           VARCHAR(50),
    created_at                TIMESTAMP,
    updated_at                TIMESTAMP,

    CONSTRAINT uk_provider_ad_no UNIQUE (provider_name, advertisement_no)
    );

CREATE INDEX IF NOT EXISTS idx_advertisement_no ON jobs (advertisement_no);
CREATE INDEX IF NOT EXISTS idx_jobs_provider    ON jobs (provider_name);
CREATE INDEX IF NOT EXISTS idx_jobs_status      ON jobs (status);