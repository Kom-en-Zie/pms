package nl.komenzie.pms

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test

/**
 * Smoke test for the framework wiring: the application boots, connects to PostgreSQL
 * (via Dev Services), runs Flyway and reports itself ready.
 */
@QuarkusTest
class HealthCheckTest {
    @Test
    fun `application reports ready`() {
        given()
            .`when`()
            .get("/q/health/ready")
            .then()
            .statusCode(200)
    }
}
