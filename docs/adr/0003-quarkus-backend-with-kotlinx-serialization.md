# 0003. Quarkus 4 backend with kotlinx.serialization

- Status: Accepted
- Date: 2026-11-25

## Context

The backend is Kotlin on Quarkus. In November 2026 two lines were available: Quarkus 3.40.x and the
freshly released Quarkus 4.0.0. PMS has no code yet and will not reach production for a while.

The DTOs in `shared` must serialise identically on the backend (JVM) and in the client (Wasm).
Jackson only exists on the JVM.

## Decision

- Start on **Quarkus 4.0**. We use the core BOM `io.quarkus:quarkus-bom` until the 4.x platform BOM
  (`io.quarkus.platform:quarkus-bom`) is published.
- Target **JDK 25** (current LTS) via Gradle toolchains.
- Use **Quarkus REST** with **`quarkus-rest-kotlin-serialization`**, so the backend uses
  kotlinx.serialization, exactly like the client.
- **Align Kotlin, kotlinx-coroutines and kotlinx-serialization with the versions in the Quarkus BOM.**
  The backend uses `enforcedPlatform(quarkus-bom)`, which forces those versions at runtime.

## Consequences

- No major-version migration right after we start building features.
- Early adopter risk: Quarkus 4.0.0 is new (it ships a Hibernate ORM 8 release candidate, for
  example). If we hit a blocking bug, falling back to Quarkus 3.40 is cheap while the codebase is small.
- `@Serializable` DTOs from `shared` work unchanged in REST resources.
- Kotlin upgrades follow Quarkus upgrades. Dependabot is configured to leave Kotlin and kotlinx alone.
