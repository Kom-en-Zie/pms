# Development guide

## Prerequisites

| Tool | Version | Notes |
|------|---------|-------|
| JDK  | 25 (latest LTS) | Used for compiling and for running Gradle itself (`gradle/gradle-daemon-jvm.properties`). Gradle downloads it automatically if it is missing. |
| Docker | Recent, with Compose v2 | Needed for Quarkus Dev Services (dev and test database) and for the full stack. |
| IDE  | IntelliJ IDEA (recommended) or Android Studio | Open the repository root as a Gradle project. The Kotlin Multiplatform plugin helps with Compose. |

You do **not** need a local Gradle, Node.js or PostgreSQL install. The Gradle wrapper, the Kotlin
Gradle plugin and Docker take care of those.

## Running the app

### Option A: full stack in Docker (closest to production)

```bash
cp .env.example .env            # once; set PMS_DB_PASSWORD
docker compose up --build       # http://localhost:8080
docker compose down             # stop (data is kept in the db-data volume)
docker compose down -v          # stop and DELETE the database volume
```

### Option B: live development (fast feedback)

Run the backend and the web app in two terminals:

```bash
# Terminal 1: backend in Quarkus dev mode, http://localhost:8080
# Dev Services starts a throwaway PostgreSQL 19 container automatically.
./gradlew :backend:quarkusDev

# Terminal 2: web app with webpack dev server, http://localhost:8081
# /api is proxied to the backend on :8080 (composeApp/webpack.config.d/dev-server.js).
./gradlew :composeApp:wasmJsBrowserDevelopmentRun --continuous
```

Useful dev-mode URLs:

- Dev UI: <http://localhost:8080/q/dev-ui>
- Swagger UI: <http://localhost:8080/q/swagger-ui>
- Health: <http://localhost:8080/q/health>

In the `quarkusDev` terminal, press `r` to re-run tests, or `h` for all shortcuts.

## Common Gradle tasks

| Task | What it does |
|------|--------------|
| `./gradlew build` | Compile everything and run all tests |
| `./gradlew spotlessCheck` / `spotlessApply` | Check / fix formatting (ktlint) |
| `./gradlew :backend:quarkusDev` | Backend with live reload |
| `./gradlew :backend:test` | Backend tests (needs Docker) |
| `./gradlew :backend:quarkusBuild` | Build the backend fast-jar into `backend/build/quarkus-app` |
| `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` | Web app dev server |
| `./gradlew :composeApp:wasmJsBrowserDistribution` | Production web bundle into `composeApp/build/dist/wasmJs/productionExecutable` |
| `./gradlew :shared:allTests` | Shared-module tests on every target |

## Database migrations

Flyway applies `backend/src/main/resources/db/migration/V<n>__<description>.sql` at startup.

1. Add the next file, e.g. `V3__add_location_to_equipment.sql`.
2. Write plain PostgreSQL DDL. Prefer `timestamptz`, `uuid` and `text`, and give every constraint
   an explicit name.
3. Restart `quarkusDev` (or press `s`). Dev Services recreates the database, so you start clean.
4. **Never modify a migration once it is on `main`.** Fix forward with a new migration.

Hibernate does not create or update tables (`schema-management.strategy=none`). If an entity
doesn't match the schema, add a migration.

## Testing

- **shared**: `kotlin.test` in `commonTest`. These tests run on the JVM and Wasm.
- **backend**: `@QuarkusTest` with REST Assured, against a real PostgreSQL started by Dev Services.
  Don't mock the database.
- **composeApp**: unit-test view models and repositories in `commonTest`, with `kotlinx-coroutines-test`.

CI (`.github/workflows/ci.yml`) runs `spotlessCheck build` and builds both Docker images on every
push and pull request.

## Code style

- Kotlin [official code style](https://kotlinlang.org/docs/coding-conventions.html), enforced by
  ktlint through Spotless. Run `./gradlew spotlessApply` before committing.
- `.editorconfig` holds the shared editor settings. IntelliJ picks it up automatically.
- `@Composable` functions use PascalCase (ktlint is configured to allow this).
- Code, comments, commit messages and docs are written in **English**. UI text lives in
  `composeApp/src/commonMain/composeResources/values*/strings.xml`, never hard-coded in Kotlin.

## Dependencies

- All versions live in `gradle/libs.versions.toml`.
- **Kotlin, kotlinx-coroutines and kotlinx-serialization must match the Quarkus BOM.** The backend
  enforces that BOM, so a mismatch would compile `shared` against one version and run it against
  another. When you upgrade Quarkus, look up the versions in its BOM and update these together.
  Dependabot ignores them on purpose.
- **The JDK is always the latest LTS** (currently 25). Upgrade the toolchain (`jvmToolchain`,
  `jvmTarget`), the daemon (`./gradlew updateDaemonJvm --jvm-version=<n>`), the Docker base images
  and CI together. See [ADR 0008](adr/0008-latest-jdk-lts.md).
- Backend library versions come from the Quarkus BOM. Don't add versions to Quarkus extensions.

## Adding Android or iOS later

The project is laid out so that this is additive. Nothing needs to move.

1. Add the target(s) to `shared/build.gradle.kts` and `composeApp/build.gradle.kts`.
2. Add the platform entry point (`androidMain` / `iosMain`), and an `iosApp/` Xcode project for iOS.
3. Add `androidMain` / `iosMain` Ktor engines (OkHttp / Darwin).
4. Introduce API versioning first (see [architecture](architecture.md#api-conventions)).

See [ADR 0002](adr/0002-kotlin-multiplatform-code-sharing.md).

## Troubleshooting

- **`Could not find a valid Docker environment`** when starting dev mode or tests: start Docker.
  Dev Services needs it.
- **Port 8080 already in use**: stop the other process, or run
  `./gradlew :backend:quarkusDev -Dquarkus.http.port=8090`. Also change the proxy target in
  `composeApp/webpack.config.d/dev-server.js` to match.
- **Blank page in the browser**: check the console. The browser needs WasmGC support (current
  Chrome, Edge, Firefox, or Safari 18.2+).
