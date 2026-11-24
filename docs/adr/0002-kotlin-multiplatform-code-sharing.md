# 0002. Kotlin Multiplatform for maximal code sharing

- Status: Accepted
- Date: 2026-11-25

## Context

PMS starts as a web app. Android and iOS apps are likely later. The team is small, so every piece of
logic that is written twice (backend vs. client, web vs. mobile) is a maintenance cost and a source
of bugs. The backend is Kotlin (Quarkus) and the frontend is Kotlin (Compose Multiplatform).

## Decision

Use a Gradle multi-module **Kotlin Multiplatform** build with three modules:

- **`shared`**: a KMP library targeting `jvm` (for the backend) and `wasmJs` (for the web client).
  Mobile targets will be added later. It contains API DTOs, API paths, domain types, validation and
  pure business logic, with no framework dependencies.
- **`backend`**: a Kotlin/JVM Quarkus application that depends on `shared`.
- **`composeApp`**: a Compose Multiplatform application. All UI, navigation, view models and the API
  client live in `commonMain`. Only entry points are platform-specific.

Mobile targets are **not** configured yet. They require the Android SDK and Xcode, which would slow
down and complicate every build and the Docker images before there is any benefit. The structure is
ready, so adding them is purely additive (see [development guide](../development.md#adding-android-or-ios-later)).

## Consequences

- One definition of every API contract. The backend and clients cannot drift apart.
- The UI is written once for web, Android and iOS.
- `shared` must stay free of JVM-only or browser-only APIs. Code review must watch for this.
- Kotlin and kotlinx versions are coupled across the whole build (see [ADR 0003](0003-quarkus-backend-with-kotlinx-serialization.md)).
- A separate client-logic module (`client` without Compose) can be split out of `composeApp` if
  it ever becomes useful, e.g. for a non-Compose consumer.
