# Frontend API Contract

This document describes the backend contract the frontend can rely on today.

## Base URL

Default local base URL:

```text
http://localhost:8080
```

## Authentication

- Authentication method: HTTP Basic
- CSRF: disabled
- Use browser login or a basic-auth capable client during development

## Public endpoints

### `GET /`

Returns paginated public short links.

Response shape:

```json
{
  "data": [
    {
      "id": 1,
      "shortKey": "abc123",
      "originalUrl": "https://example.com",
      "isPrivate": false,
      "expiresAt": "2026-03-20T10:15:30Z",
      "clickCount": 12,
      "createdAt": "2026-03-19T10:15:30Z",
      "createdBy": {
        "id": 7,
        "name": "Nishant",
        "email": "nishant@example.com"
      }
    }
  ],
  "pageNumber": 1,
  "totalPages": 3,
  "totalElements": 25,
  "isFirst": true,
  "isLast": false,
  "hasNext": true,
  "hasPrevious": false
}
```

### `POST /register`

Registers a user.

Request body:

```json
{
  "email": "user@example.com",
  "password": "secret",
  "name": "User Name"
}
```

Success response:

```text
Registration successful! Please login.
```

### `POST /short-urls`

Creates a short URL for the signed-in user.

Request body:

```json
{
  "originalUrl": "https://example.com",
  "isPrivate": false,
  "expirationInDays": 30
}
```

Response is a `ShortUrlDto`.

### `GET /s/{shortKey}`

Redirects to the original URL with HTTP `302 Found`.

### `GET /my-urls`

Returns paginated URLs owned by the authenticated user.

### `POST /delete-urls`

Deletes a list of user-owned URL ids.

Request body:

```json
[
  1,
  2,
  3
]
```

## Admin endpoint

### `GET /admin/dashboard`

Returns paginated URLs for admin review.

## Data contracts

### `ShortUrlDto`

- `id: number`
- `shortKey: string`
- `originalUrl: string`
- `isPrivate: boolean`
- `expiresAt: string | null`
- `clickCount: number`
- `createdAt: string`
- `createdBy: UserDto | null`

### `UserDto`

The backend uses a `UserDto` reference in `ShortUrlDto.createdBy`. Keep the frontend tolerant of `null` and unknown fields until the DTO is finalized in a dedicated schema document.

## Validation hints

- `RegisterUserRequest.email` must be present and valid
- `RegisterUserRequest.password` must be present
- `RegisterUserRequest.name` must be present
- `CreateShortUrlForm.originalUrl` is required
- `CreateShortUrlForm.expirationInDays` must be between `1` and `375` when provided

## Error handling

- Validation failures may return `400`
- unauthenticated access to protected routes returns `401`
- unauthorized admin access returns `403`
- missing short keys surface as redirect failures or server errors depending on endpoint
