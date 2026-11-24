# 0007. Docker everywhere, single-origin deployment behind nginx

- Status: Accepted
- Date: 2026-11-25

## Context

Everything should run in Docker. The web client is a static bundle and the backend is an HTTP API.
Serving them from different origins would need CORS, and would complicate cookie-based
authentication later.

## Decision

- One multi-target `Dockerfile` at the repository root builds:
  - `backend`: the Quarkus fast-jar on `eclipse-temurin:25-jre`, running as a non-root user.
  - `frontend`: the Wasm production bundle on `nginx-unprivileged`, which also reverse-proxies
    `/api/*` to the backend.
- `compose.yaml` runs `db`, `backend` and `frontend`. Only the frontend port is published.
- Secrets come from a git-ignored `.env` file (template: `.env.example`).
- For live development, Quarkus Dev Services runs PostgreSQL in Docker. The backend and web dev
  servers run through Gradle for hot reload.

## Consequences

- The browser sees a single origin: no CORS, and session cookies work.
- The build context is the repository root, because both images need `shared`. `.dockerignore`
  keeps the context small and leaves out `.env`.
- TLS termination and the production hosting setup are not decided yet (see the roadmap).
- JVM mode only for now. A native-image backend is possible later if memory footprint matters.
