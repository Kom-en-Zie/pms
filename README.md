# PMS

[![CI](https://github.com/Kom-en-Zie/pms/actions/workflows/ci.yml/badge.svg)](https://github.com/Kom-en-Zie/pms/actions/workflows/ci.yml)

PMS is the system used by the technical teams of **Kom en Zie**, mainly the media team.

> **Status: foundation only.** The frameworks, build, Docker setup and documentation are in place.
> No features exist yet. See the [roadmap](docs/roadmap.md).

## Principles

- **Simple first.** Every screen should be obvious to a volunteer who uses it once a month.
- **Mobile first, desktop still matters.** Layouts are designed for phones first and then scale up
  to tablets and desktops. See [UX guidelines](docs/ux-guidelines.md).
- **Write it once.** Kotlin runs everywhere in the stack, so API contracts and business rules are
  shared between backend and clients, and the UI is shared between web, Android and iOS.
- **Everything runs in Docker.** You can run the whole stack with one command.

## Tech stack

| Layer    | Technology                                                                 |
|----------|----------------------------------------------------------------------------|
| Backend  | Kotlin · [Quarkus](https://quarkus.io) 4 · Hibernate ORM (Panache) · Flyway |
| Frontend | Kotlin · [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/) (Wasm) · Material 3 · Ktor client |
| Shared   | Kotlin Multiplatform · kotlinx.serialization · kotlinx-datetime            |
| Database | PostgreSQL 19                                                              |
| Runtime  | Docker / Docker Compose · nginx (static files + `/api` proxy)              |
| Build    | Gradle 9 (Kotlin DSL, version catalog) · JDK 25                            |

Clients: **web** for now. **Android and iOS** are planned, using the same Compose code.
Authentication: **none during the first development phase**. OIDC with Planning Center comes later
(see [authentication](docs/authentication.md)).

## Repository layout

```
pms/
├── shared/        Kotlin Multiplatform code used by backend AND clients (DTOs, API paths, domain rules)
├── backend/       Quarkus REST API (JVM)
├── composeApp/    Compose Multiplatform client (web now; Android/iOS later)
├── docker/        nginx configuration for the frontend image
├── docs/          Architecture, development guide, ADRs, UX guidelines
├── Dockerfile     Multi-target build: `backend` and `frontend` images
└── compose.yaml   Full stack: db + backend + frontend
```

## Quick start (Docker)

You need Docker with Compose v2.

```bash
cp .env.example .env          # then set PMS_DB_PASSWORD
docker compose up --build
```

Open <http://localhost:8080>.

## Development

You need **JDK 25** (Gradle can download it for you) and a running **Docker** daemon, which Quarkus
Dev Services uses to start PostgreSQL.

```bash
./gradlew :backend:quarkusDev                       # backend with live reload  → http://localhost:8080
./gradlew :composeApp:wasmJsBrowserDevelopmentRun   # web app with hot reload    → http://localhost:8081
./gradlew build                                     # compile + test everything
./gradlew spotlessApply                             # format code (ktlint)
```

See the [development guide](docs/development.md) for the full workflow.

## Documentation

- [Architecture](docs/architecture.md): modules, code sharing, runtime topology, conventions
- [Development guide](docs/development.md): setup, daily workflow, migrations, testing
- [Authentication](docs/authentication.md): phase 1 (none) and phase 2 (Planning Center OIDC)
- [UX guidelines](docs/ux-guidelines.md): simplicity, mobile first, adaptive layouts
- [Roadmap](docs/roadmap.md): phases and open questions
- [Architecture decision records](docs/adr/README.md)
- [Contributing](CONTRIBUTING.md) · [Security policy](SECURITY.md)

## License

[GNU Affero General Public License v3.0](LICENSE). If you run a modified version as a network service,
you must make its source code available to the users of that service.
