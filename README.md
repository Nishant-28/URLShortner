# ChotaURL

ChotaURL is a Spring Boot URL shortener backed by PostgreSQL and Flyway migrations. It provides public link browsing, authenticated short-link creation, user-owned link management, and an admin review dashboard.

## Project Layout

- `backend/` - Spring Boot application, security, persistence, and web controllers
- `frontend/` - reserved for the UI implementation
- `docs/` - backend overview, API contract, and local development notes
- `docker-compose.yml` - local PostgreSQL stack and optional full application stack
- `.env.example` - sample runtime environment values for local development

## Features

- public landing page with paginated short links
- user registration and HTTP Basic authentication
- authenticated short URL creation
- redirect handling for `/{shortKey}` links
- user-owned URL listing and deletion
- admin dashboard access for reviewing URLs
- PostgreSQL persistence with Flyway schema and seed migrations

## Architecture

The backend uses a conventional Spring stack:

- Spring Web MVC for controllers
- Spring Security with HTTP Basic auth
- Spring Data JPA for persistence
- PostgreSQL for primary storage
- Flyway for schema creation and seed data
- Spring Boot Docker Compose integration for local database startup

The main configuration values are:

- `app.base-url`
- `app.default-expiry-in-days`
- `app.validate-original`
- `app.page-size`

Database settings are provided through:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

## Quick Start

### Prerequisites

- Java 21
- Maven wrapper in `backend/`
- Docker and Docker Compose

### Option 1: Backend only

Start PostgreSQL:

```bash
docker compose up postgres
```

Run the backend:

```bash
cd backend
./mvnw spring-boot:run
```

The app runs at `http://localhost:8080`.

### Option 2: Local profile with auto-started Postgres

The `local` profile uses Spring Boot Docker Compose integration to start PostgreSQL automatically.

```bash
cd backend
SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run
```

### Option 3: Full stack with Docker

Run both the database and the backend in containers:

```bash
docker compose --profile app up --build
```

## Configuration

Copy `.env.example` into your local environment and adjust values as needed.

### Backend environment variables

- `DB_URL` - JDBC connection string for PostgreSQL
- `DB_USERNAME` - database username
- `DB_PASSWORD` - database password
- `APP_BASE_URL` - canonical public base URL used in generated links
- `APP_VALIDATE_ORIGINAL` - enables/disables original URL validation
- `SPRING_PROFILES_ACTIVE` - use `local` for the local development profile

### Frontend environment variables

- `VITE_API_BASE_URL` - backend base URL used by the frontend

## Migrations

Database schema and seed data live in `backend/src/main/resources/db/migration`.

- `V1__create_tables.sql` creates the tables
- `V2__insert_sample_data.sql` inserts sample data
- `V3__update_user_passwords.sql` updates stored user passwords

Flyway runs automatically on startup.

## API Summary

### Public routes

- `GET /` - paginated public links
- `POST /register` - user registration
- `GET /s/{shortKey}` - redirect to the original URL

### Authenticated routes

- `POST /short-urls` - create a short URL
- `GET /my-urls` - list the authenticated user's URLs
- `POST /delete-urls` - delete selected user-owned URLs

### Admin routes

- `GET /admin/dashboard` - view URLs for admin review

## Security Model

- Authentication is HTTP Basic
- CSRF is disabled
- Public routes are accessible without login
- Protected routes require credentials
- Admin routes require the `ADMIN` role

## Useful URLs

- App: `http://localhost:8080`
- PostgreSQL: `localhost:5432`

## Documentation

- [Backend overview](docs/backend-overview.md)
- [API contract for frontend](docs/frontend-api.md)
- [Frontend handoff guide](docs/frontend-handoff.md)
- [Local development guide](docs/local-development.md)
- [Environment template](.env.example)

## Notes for the Frontend Team

- Authentication is HTTP Basic
- CSRF is disabled
- Public routes are available without login
- Protected routes require credentials
- API responses are JSON for data endpoints and simple text for some success/error flows
