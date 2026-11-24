# Security policy

## Reporting a vulnerability

**Do not open a public issue for security problems.**

Report vulnerabilities privately through GitHub:
**Security** tab → **Report a vulnerability** on
[github.com/Kom-en-Zie/pms](https://github.com/Kom-en-Zie/pms/security/advisories/new).

Please include what you found, how to reproduce it, and the impact you expect. We will acknowledge
your report as soon as possible. PMS is maintained by volunteers, so please allow some time for a fix.

## Supported versions

Only the latest version on `main` receives security fixes.

## Current state

PMS is in early development. Authentication is **intentionally not implemented yet** (see
[docs/authentication.md](docs/authentication.md)). This is a known limitation, not a
vulnerability: PMS must not be deployed on a publicly reachable network until authentication is in
place.
