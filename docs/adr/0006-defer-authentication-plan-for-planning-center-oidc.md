# 0006. Defer authentication, plan for Planning Center OIDC

- Status: Accepted
- Date: 2026-11-25

## Context

The teams already use Planning Center, so it is the natural identity provider (OIDC). Setting it up
needs decisions and access (OAuth application registration, role mapping) that should not block
early feature development.

## Decision

- **Phase 1:** no real authentication. Endpoints are still written against Quarkus security
  annotations and a `CurrentUser` abstraction. A dev-only mechanism supplies a fixed identity.
- **Phase 2:** `quarkus-oidc` with Planning Center. The web client uses the **BFF pattern**
  (`application-type=web-app`, HttpOnly session cookie, tokens stay on the server). Mobile clients
  later use bearer tokens (`application-type=hybrid`).

Details are in [authentication.md](../authentication.md).

## Consequences

- Features can be built and demoed right away.
- **PMS must not be exposed publicly until phase 2 is done.** The dev mechanism must refuse to run
  in the `prod` profile unless explicitly overridden.
- Because the code already uses the security annotations, phase 2 mostly changes configuration
  rather than touching endpoints.
- Users are stored with nullable `(issuer, subject)` columns, so phase 1 data can be linked to
  Planning Center accounts later.
