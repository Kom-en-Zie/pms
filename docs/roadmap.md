# Roadmap

## Phase 0: Foundation ✅ (current)

- Gradle multi-module build: `shared`, `backend`, `composeApp`
- Quarkus backend wired for PostgreSQL, Flyway, OpenAPI and health checks
- Compose Multiplatform web (Wasm) client skeleton
- Docker images and `compose.yaml` for the full stack
- CI (lint, build, test, Docker images) and Dependabot
- Documentation and architecture decision records

## Phase 1: First features (development, no authentication)

- Define the first feature set together with the media team (see open questions)
- Design system: theme (light/dark), typography, core components
- App shell: adaptive navigation (`NavigationSuiteScaffold`), routing, error/loading patterns
- Development identity and `CurrentUser` abstraction (see [authentication](authentication.md#phase-1-development-no-authentication))
- First domain features, end to end: migration → entity → service → REST → shared DTO → screen

## Phase 2: Authentication with Planning Center

- Resolve the [open questions](authentication.md#open-questions-resolve-before-implementing)
- `quarkus-oidc` in web-app (BFF) mode with Planning Center
- Map Planning Center identities to PMS users, and define roles and permissions
- First production deployment (only after this phase, see the phase 1 warning)

## Phase 3: Mobile apps

- API versioning
- Android and iOS targets in `shared` and `composeApp`
- Native login (authorization code + PKCE) and secure token storage
- Store distribution (Google Play / App Store, or internal distribution)

## Open questions

| Topic | Question |
|-------|----------|
| Scope | Which features does the media team need first? Which other technical teams follow? |
| Hosting | Where will PMS run in production (own server, VPS, cloud)? Who maintains it? Where are backups stored? |
| Language | Is Dutch-only UI enough, or is English needed as well? |
| Roles | Which roles and permissions exist (e.g. admin, team lead, volunteer)? |
| Planning Center | Beyond login, should PMS read data from Planning Center (people, teams, service plans)? |
| Web support | Do we need a JavaScript fallback for browsers without WasmGC (very old phones)? |
