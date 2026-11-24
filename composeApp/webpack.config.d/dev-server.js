// Applied by the Kotlin Gradle plugin on top of the generated webpack config.
// Only affects `wasmJsBrowserDevelopmentRun` / `wasmJsBrowserRun` (the dev server).
//
// Runs the web dev server on 8081 so it does not clash with `quarkusDev` on 8080, and forwards
// /api to the backend, which mirrors the nginx setup in Docker (same origin, no CORS).
if (config.devServer) {
    config.devServer.port = 8081;
    config.devServer.historyApiFallback = true;
    config.devServer.proxy = [
        { context: ["/api"], target: "http://localhost:8080" },
    ];
}
