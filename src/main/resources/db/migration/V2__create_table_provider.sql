CREATE TABLE IF NOT EXISTS providers(
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(100) NOT NULL,
    full_name           VARCHAR(255) NOT NULL,
    url                 VARCHAR(1000) NOT NULL,
    active              BOOLEAN,
    frequency_minutes   INTEGER,
    updated_at          TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_name on providers (name);