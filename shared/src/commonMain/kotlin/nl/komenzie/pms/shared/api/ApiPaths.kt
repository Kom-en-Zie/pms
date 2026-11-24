package nl.komenzie.pms.shared.api

/**
 * HTTP paths shared by the backend (resource definitions) and the clients (API calls),
 * so a route can never drift between the two sides.
 */
object ApiPaths {
    /** Prefix for every REST endpoint. Must match `quarkus.rest.path` in the backend. */
    const val BASE = "/api"
}
