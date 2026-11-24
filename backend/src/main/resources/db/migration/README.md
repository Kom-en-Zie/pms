# Database migrations

Flyway applies the SQL files in this directory at startup (`quarkus.flyway.migrate-at-start=true`).

- Name files `V<version>__<description>.sql`, e.g. `V1__create_equipment.sql`.
- Use plain sequential integers for the version (`V1`, `V2`, …).
- **Never edit a migration that has been merged to `main`.** Add a new one instead.
- Hibernate never changes the schema (`schema-management.strategy=none`), so every
  table, column and index has to be created here.

See [docs/development.md](../../../../../../docs/development.md#database-migrations) for more.
