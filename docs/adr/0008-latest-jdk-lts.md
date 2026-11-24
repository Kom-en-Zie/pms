# 0008. Always use the latest JDK LTS

- Status: Accepted
- Date: 2026-11-25

## Context

We want to stay on a supported long-term JDK across the whole toolchain. Gradle itself used to run
on whatever JDK the developer had installed, so builds could behave differently between machines.

We also considered keeping Kotlin on its latest release, ahead of the version pinned by the Quarkus
BOM. We decided against that: Kotlin keeps following the Quarkus BOM, as described in
[ADR 0003](0003-quarkus-backend-with-kotlinx-serialization.md), so the backend runs the exact Kotlin
version Quarkus is tested with.

## Decision

- **JDK: always the latest LTS**, currently 25. It is used for:
  - the compile toolchain and bytecode target (`jvmToolchain`, `jvmTarget`)
  - the Gradle daemon (`gradle/gradle-daemon-jvm.properties`, generated with
    `./gradlew updateDaemonJvm --jvm-version=<n>`)
  - the Docker base images (`eclipse-temurin:<n>-jdk` / `-jre`)
  - CI (`actions/setup-java`)

## Consequences

- Every machine and CI run builds with the same JDK. Gradle downloads it if it is missing.
- When a new LTS ships, upgrade it once Quarkus, Kotlin and Gradle support it. The toolchain,
  daemon criteria, `Dockerfile` and CI change together in one pull request.
