# Local Development

## Prerequisites

- Java 21
- Maven wrapper in `backend/`
- Docker and Docker Compose

## Option 1: backend only

Start PostgreSQL:

```bash
docker compose up postgres
```

Run the app:

```bash
cd backend
./mvnw spring-boot:run
```

## Option 2: Docker Compose

Run both backend and database:

```bash
docker compose --profile app up --build
```

## Useful URLs

- App: `http://localhost:8080`
- PostgreSQL: `localhost:5432`

## Environment variables

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `APP_BASE_URL`
- `APP_VALIDATE_ORIGINAL`
- `SPRING_PROFILES_ACTIVE`

## Notes

- `application-local.properties` enables Spring Boot Docker Compose auto-start for local development
- Flyway runs migrations from `backend/src/main/resources/db/migration`
- `docker-compose.yml` at the repo root is the main local stack definition
