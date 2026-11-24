# Contributing to PMS

Thanks for helping the Kom en Zie technical teams! This guide covers the basics. The
[development guide](docs/development.md) has the technical details.

## Before you start

- Read the [architecture overview](docs/architecture.md) and the [UX guidelines](docs/ux-guidelines.md).
- For anything bigger than a small fix, open an issue first so we can agree on the approach.
- Architectural changes come with an [ADR](docs/adr/README.md).

## Workflow

1. Create a branch from `main`: `feature/<short-name>`, `fix/<short-name>` or `docs/<short-name>`.
2. Make your change in small, focused commits. Write commit messages in the imperative mood
   ("Add equipment list", not "Added …").
3. Run `./gradlew spotlessApply build` locally.
4. Open a pull request against `main` and fill in what changed and why. Add screenshots for UI
   changes, on both a phone-sized and a desktop-sized window.
5. CI must be green before merging.

## Checklist

- [ ] Shared logic and DTOs live in `shared`, not duplicated in backend and client.
- [ ] Schema changes come as a **new** Flyway migration. Existing migrations are not edited.
- [ ] UI text is in `strings.xml`, not hard-coded.
- [ ] The UI works at 360 dp wide and on desktop, in light and dark theme.
- [ ] Tests cover new behaviour.
- [ ] Docs and ADRs are updated where needed.

## This repository is public

- **Never commit secrets** (passwords, tokens, client secrets, `.env` files) or personal data of
  church members. Use obviously fictional test data.
- If you commit a secret by accident, tell a maintainer right away. The secret must be rotated.
  Removing it from git history is not enough.
- Report security problems privately, as described in [SECURITY.md](SECURITY.md).

## License

By contributing you agree that your contributions are licensed under the
[GNU AGPL v3.0](LICENSE).
