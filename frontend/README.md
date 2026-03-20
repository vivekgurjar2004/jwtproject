# JWT Auth Demo (React + Vite)

## Pages

- `POST /api/auth/register` from the Register page
- `POST /api/auth/login` from the Login page
- `GET /api/profile` from the Profile page (JWT protected)

## Run (dev)

From the `frontend/` folder:

```bash
npm run dev
```

Then open:
- `http://localhost:5173/login`

## API base URL

By default, the Vite dev server proxies `/api/*` to your Spring Boot backend on `http://localhost:8080`.

Optional:

Create `frontend/.env`:
```bash
VITE_API_BASE_URL=http://localhost:8080
```

