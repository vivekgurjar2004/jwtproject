# JWT Auth Demo (Spring Boot, In-Memory)

Spring Boot (Maven) + Java 17

## Endpoints

`POST /api/auth/register`

Request JSON:
```json
{
  "username": "alice",
  "email": "alice@example.com",
  "password": "password123"
}
```

`POST /api/auth/login`

Request JSON:
```json
{
  "username": "alice",
  "password": "password123"
}
```

Response:
```json
{
  "token": "<jwt>",
  "tokenType": "Bearer"
}
```

`GET /api/profile` (JWT protected)

Header:
`Authorization: Bearer <jwt>`

## How to run

If you have Maven installed:

```bash
mvn spring-boot:run
```

Then test using the `curl` examples above.

## Notes

This project uses an in-memory `ConcurrentHashMap` user store (no database). On restart, users are lost.

