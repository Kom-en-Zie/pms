# CLAUDE.md
Yes, Claude is used in the creation of this application.
No, that doesn't mean you should contribute without technical knowledge.
You're not allowed to contribute if you don't know how to code.

Guidance for AI coding agents working in this repository. Humans: see README.md and docs/.

## Project

PMS (Project Management System) for the technical teams (mainly the media team) of Kom en Zie. Kotlin across the stack:
Quarkus 4 backend, Compose Multiplatform client (web/Wasm now; Android/iOS later), shared KMP
module, PostgreSQL 19, everything in Docker. **The repository is public.** Never add secrets, real
credentials or personal data, including in tests, fixtures, docs or commit messages.

## Modules

- `shared/`: KMP (`jvm`, `wasmJs`). DTOs, `ApiPaths`, domain types, validation. No framework deps.
- `backend/`: Quarkus (Kotlin/JVM). Feature packages under `nl.komenzie.pms.<feature>`.
- `composeApp/`: Compose Multiplatform. All UI in `commonMain`; `wasmJsMain` only has the entry point.

## Commands

```bash
./gradlew build                                     # compile + all tests (backend tests need Docker)
./gradlew spotlessApply                             # format (ktlint); CI runs spotlessCheck
./gradlew :backend:quarkusDev                       # backend dev mode, :8080 (Dev Services Postgres)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun   # web dev server, :8081, proxies /api → :8080
docker compose up --build                           # full stack on :8080 (needs .env from .env.example)
```

## Rules

- Define DTOs and validation **once** in `shared`, and reuse them in backend and client.
- Schema changes go in a **new** Flyway migration (`backend/src/main/resources/db/migration/V<n>__*.sql`).
  Never edit an existing migration. Hibernate must not manage the schema.
- Kotlin, kotlinx-coroutines and kotlinx-serialization versions must match the Quarkus BOM
  (`gradle/libs.versions.toml`). Don't bump them independently.
- REST endpoints live under `/api`. JSON uses kotlinx.serialization. Errors use RFC 9457 problem details.
- UI: mobile first and simple (docs/ux-guidelines.md). Use window size classes, not platform checks.
  Strings go in `composeResources`, Dutch by default.
- Auth is deliberately absent in phase 1. Still use Quarkus security annotations and the planned
  `CurrentUser` abstraction (docs/authentication.md).
- Significant architectural changes need an ADR in `docs/adr/`.
