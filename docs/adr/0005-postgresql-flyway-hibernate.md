# 0005. PostgreSQL 19, Flyway migrations, Hibernate ORM with Panache

- Status: Accepted
- Date: 2026-11-25

## Context

The database is PostgreSQL 19. We need a reliable way to evolve the schema across developer
machines, CI and production, plus a productive data-access layer in Kotlin.

## Decision

- **PostgreSQL 19** everywhere: Dev Services in dev and test, and `postgres:19-alpine` in Docker.
- **Flyway** (`quarkus-flyway` + `quarkus-flyway-postgresql`) owns the schema and runs migrations at
  startup.
- **Hibernate ORM with Panache for Kotlin** (`quarkus-hibernate-orm-panache-kotlin`), blocking JDBC.
  Hibernate never generates or alters the schema (`schema-management.strategy=none`).
- Tests run against a **real PostgreSQL** (Dev Services), not H2 or mocks.

## Consequences

- Schema changes are explicit, reviewed SQL. You can't change the schema by accident from an
  entity.
- Blocking JDBC is simpler than reactive. Expected load is low (one church's technical teams), so
  reactive brings no meaningful benefit.
- Running the tests requires Docker. CI runners provide it.
- If the ORM gets in the way for read-heavy screens, native SQL or projections through Panache stay
  available. We do not need a second data-access library.
