# ChotaURL

ChotaURL is a Spring Boot short-link service backed by PostgreSQL and Flyway migrations.

## What this repo contains

- `backend/`: Spring Boot application, security, persistence, and short-url APIs
- `frontend/`: reserved for the UI implementation
- `docker-compose.yml`: local PostgreSQL and backend application stack

## Quick start

### Local development with Docker

```bash
docker compose up postgres
cd backend
./mvnw spring-boot:run
```

The app runs at `http://localhost:8080`.

### Full stack with Docker

```bash
docker compose --profile app up --build
```

## Backend responsibilities

- user registration
- short URL creation
- public landing page data
- user-owned URL listing and deletion
- redirect handling for short keys
- admin dashboard access

## Documentation

- [Backend overview](docs/backend-overview.md)
- [API contract for frontend](docs/frontend-api.md)
- [Frontend handoff guide](docs/frontend-handoff.md)
- [Local development guide](docs/local-development.md)
- [Environment template](.env.example)

## Notes for the frontend team

- Authentication is HTTP Basic
- CSRF is disabled
- Public routes are available without login
- Protected routes require credentials
- API responses are JSON for data endpoints and simple text for some success/error flows
# URLShortner
