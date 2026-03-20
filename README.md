# ChotaURL

A Spring Boot URL shortener backed by PostgreSQL, with Flyway-managed schema migrations. The service exposes a public browsing interface, HTTP Basic authenticated link creation, per-user link management, and an admin review dashboard.

---

## Project Structure
```
.
├── backend/          # Spring Boot application: security, persistence, controllers
├── frontend/         # UI implementation (reserved)
├── docs/             # Backend overview, API contract, local development notes
├── docker-compose.yml
└── .env.example
```

---

## Features

- Paginated public listing of active short links
- User registration and HTTP Basic authentication
- Authenticated short URL creation with configurable expiry
- Redirect handling via `GET /s/{shortKey}`
- Per-user URL listing and deletion
- Admin dashboard for reviewing all URLs
- PostgreSQL persistence with Flyway schema and seed migrations

---

## Architecture

The backend follows a conventional layered Spring architecture:

| Layer | Technology |
|---|---|
| Web | Spring Web MVC |
| Security | Spring Security (HTTP Basic) |
| Persistence | Spring Data JPA |
| Database | PostgreSQL |
| Migrations | Flyway |
| Local dev DB | Spring Boot Docker Compose integration |

---

## Quick Start

**Prerequisites:** Java 21, Maven wrapper (`backend/mvnw`), Docker and Docker Compose.

### Option 1 — Backend only

Start the database, then the application:
```bash
docker compose up postgres

cd backend
./mvnw spring-boot:run
```

### Option 2 — Local profile with automatic database startup

The `local` Spring profile delegates database startup to Spring Boot's Docker Compose integration:
```bash
cd backend
SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run
```

### Option 3 — Full stack in Docker

Build and run both the database and the application as containers:
```bash
docker compose --profile app up --build
```

The application is available at `http://localhost:8080`. PostgreSQL binds to `localhost:5432`.

---

## Configuration

Copy `.env.example` and adjust values before running locally.

### Backend

| Variable | Description |
|---|---|
| `DB_URL` | JDBC connection string for PostgreSQL |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `APP_BASE_URL` | Canonical public base URL used in generated short links |
| `APP_VALIDATE_ORIGINAL` | Enables or disables validation of the original URL on creation |
| `SPRING_PROFILES_ACTIVE` | Set to `local` for the local development profile |

Application-level tuning is done via `application.properties` or environment overrides:

| Property | Description |
|---|---|
| `app.base-url` | Mirrors `APP_BASE_URL` |
| `app.default-expiry-in-days` | Default link lifetime in days |
| `app.validate-original` | Mirrors `APP_VALIDATE_ORIGINAL` |
| `app.page-size` | Number of items per page on public listing |

### Frontend

| Variable | Description |
|---|---|
| `VITE_API_BASE_URL` | Backend base URL consumed by the frontend build |

---

## Database Migrations

Migrations live in `backend/src/main/resources/db/migration` and run automatically on startup via Flyway.

| Migration | Purpose |
|---|---|
| `V1__create_tables.sql` | Creates the full schema |
| `V2__insert_sample_data.sql` | Inserts seed data for local development |
| `V3__update_user_passwords.sql` | Updates stored user password hashes |

---

## API Reference

### Public

| Method | Path | Description |
|---|---|---|
| `GET` | `/` | Paginated listing of public short links |
| `POST` | `/register` | User registration |
| `GET` | `/s/{shortKey}` | Redirect to the original URL |

### Authenticated

| Method | Path | Description |
|---|---|---|
| `POST` | `/short-urls` | Create a short URL |
| `GET` | `/my-urls` | List the authenticated user's URLs |
| `POST` | `/delete-urls` | Delete selected user-owned URLs |

### Admin

| Method | Path | Description |
|---|---|---|
| `GET` | `/admin/dashboard` | Review all URLs (requires `ADMIN` role) |

---

## Security

- Authentication uses HTTP Basic over all protected routes.
- CSRF protection is disabled; the API is consumed by a decoupled frontend.
- Public routes require no credentials.
- Admin routes enforce the `ADMIN` role.

---

## Documentation

- [Backend overview](docs/backend-overview.md)
- [API contract for the frontend](docs/frontend-api.md)
- [Frontend handoff guide](docs/frontend-handoff.md)
- [Local development guide](docs/local-development.md)
- [Environment template](.env.example)

---

## Notes for Frontend Consumers

