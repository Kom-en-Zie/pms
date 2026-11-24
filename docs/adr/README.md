# Architecture decision records

An ADR records one significant decision: its context, what was decided and what follows from it.
ADRs are immutable once accepted. To change a decision, write a new ADR that supersedes the old one.

| #    | Decision | Status |
|------|----------|--------|
| [0001](0001-record-architecture-decisions.md) | Record architecture decisions | Accepted |
| [0002](0002-kotlin-multiplatform-code-sharing.md) | Kotlin Multiplatform for maximal code sharing | Accepted |
| [0003](0003-quarkus-backend-with-kotlinx-serialization.md) | Quarkus 4 backend with kotlinx.serialization | Accepted |
| [0004](0004-web-first-with-compose-wasm.md) | Web first, with Compose Multiplatform on WebAssembly | Accepted |
| [0005](0005-postgresql-flyway-hibernate.md) | PostgreSQL 19, Flyway migrations, Hibernate ORM with Panache | Accepted |
| [0006](0006-defer-authentication-plan-for-planning-center-oidc.md) | Defer authentication, plan for Planning Center OIDC | Accepted |
| [0007](0007-docker-single-origin-deployment.md) | Docker everywhere, single-origin deployment behind nginx | Accepted |

## Template

Copy this into `NNNN-short-title.md`:

```markdown
# NNNN. Title

- Status: Proposed | Accepted | Superseded by [NNNN](…)
- Date: YYYY-MM-DD

## Context
What problem or force makes this decision necessary?

## Decision
What we decided, stated plainly.

## Consequences
What becomes easier, what becomes harder, and what we must now do or watch out for.
```
