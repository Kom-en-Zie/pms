# 0004. Web first, with Compose Multiplatform on WebAssembly

- Status: Accepted
- Date: 2026-11-25

## Context

The first release is web only. The UI must be shareable with future Android and iOS apps.
Compose Multiplatform can target the browser via Kotlin/Wasm (`wasmJs`) or Kotlin/JS (`js`). The
`js` target exists mainly as a fallback for browsers without WasmGC.

## Decision

- Build the web client with Compose Multiplatform for the **`wasmJs`** target only.
- Use **Material 3** with the **adaptive** libraries (`material3-adaptive`,
  `material3-adaptive-navigation-suite`) for mobile-first, size-class-driven layouts.
- Use **Jetpack Navigation for Compose Multiplatform** (`navigation-compose`). It can bind
  navigation to the browser history (back button, URLs).

## Consequences

- The same Compose code will run on Android and iOS.
- Needs a browser with **WasmGC**: current Chrome, Edge and Firefox, and Safari 18.2+ (iOS 18.2+).
  If volunteers turn out to use older devices, add the `js` target as a fallback (compatibility
  distribution). This costs extra build time.
- The UI is drawn on a canvas, not with HTML elements. Text selection, browser find-in-page and
  some accessibility tooling behave differently from a classic web page. SEO is irrelevant for an
  internal tool.
- First load is heavy (Skia runtime plus app, several MB compressed). nginx serves it gzip-compressed
  and caches hashed `.wasm` files forever, so later visits are fast. A service worker or PWA can be
  added later if needed.
