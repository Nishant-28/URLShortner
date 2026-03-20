# Backend Overview

This project is a Spring Boot backend for a short-url application.

## Current architecture

- Spring Web MVC for controllers
- Spring Security with HTTP Basic auth
- Spring Data JPA for persistence
- PostgreSQL as the primary database
- Flyway for schema and seed migrations

## Domain objects

- `User`
- `ShortUrl`
- `Role`
- `UserDto`
- `ShortUrlDto`
- `PagedResult<T>`

## Core behaviors

- Public short URLs are listed on `/`
- Authenticated users can create short URLs on `POST /short-urls`
- Short-key redirects happen on `GET /s/{shortKey}`
- Users can view their own links on `GET /my-urls`
- Users can delete their own links on `POST /delete-urls`
- Admin users can access `GET /admin/dashboard`

## Configuration

Important application properties:

- `app.base-url`
- `app.default-expiry-in-days`
- `app.validate-original`
- `app.page-size`

Database connection values are read from:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

## Security model

- `/`, `/short-urls`, `/s/**`, `/register`, and `/login` are public
- everything else requires authentication
- admin routes require the `ADMIN` role
