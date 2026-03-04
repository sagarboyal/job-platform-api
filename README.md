# Job Platform API

A Spring Boot backend that scrapes official government job portals and serves listings via GraphQL. Built to power the [Job Platform App](https://github.com/sagarboyal/job-platform-app) — no user login required.

> 🚧 Active development — features and APIs may change.

---

## Tech Stack

| | |
|---|---|
| Java + Spring Boot | Core framework |
| Spring GraphQL | Job query API |
| Spring Data JPA + PostgreSQL | Persistence |
| Flyway | Database migrations |
| Spring Security | API protection |
| Maven | Build tool |

---

## Architecture Overview

The system has two main responsibilities:

**1. Scraping Engine** — watches official job portals on a schedule, detects changes via HTML snapshot diffing, and persists new listings automatically.

**2. Job API** — exposes job data over GraphQL with support for filtering, sorting, and pagination.

```
config/          → Security, Flyway, GraphQL scalars
constants/       → App-wide constants
core/            → Job domain (entity, GraphQL, service, specs, DTOs)
scrapper/        → Providers, scheduler, watcher, change detection
payload/         → Shared response wrappers
exception/       → Global error handling
utils/           → Helpers
resources/
  graphql/       → Schema definitions
  db/migration/  → Flyway SQL (V1–V3)
```

---

## Getting Started

**Prerequisites:** Java 17+, Maven, PostgreSQL

```bash
git clone https://github.com/sagarboyal/job-platform-api.git
cd job-platform-api
```

Set your database credentials in `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/job_platform
    username: your_username
    password: your_password
```

```bash
./mvnw spring-boot:run
```

---

## API

- **Jobs** → GraphQL (filtering, sorting, pagination)
- **Providers** → REST

---

## Status

| Feature | Status |
|---|---|
| Multi-provider scraping | ✅ Done |
| Scheduled auto-refresh | ✅ Done |
| Change detection | ✅ Done |
| GraphQL with filter/sort/pagination | ✅ Done |
| Database migrations | ✅ Done |
| More providers | 🔄 Planned |
| Tests | 🔄 Planned |
| Docker support | 🔄 Planned |
