import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.quarkus)
}

dependencies {
    implementation(enforcedPlatform(libs.quarkus.bom))
    implementation(projects.shared)

    implementation(libs.quarkus.kotlin)
    implementation(libs.quarkus.arc)

    // REST API, serialized with kotlinx.serialization so DTOs in :shared work unchanged on both sides.
    implementation(libs.quarkus.rest)
    implementation(libs.quarkus.rest.kotlin.serialization)
    implementation(libs.quarkus.hibernate.validator)
    implementation(libs.quarkus.smallrye.openapi)

    // Persistence: PostgreSQL + Hibernate ORM (Panache) + Flyway migrations.
    implementation(libs.quarkus.jdbc.postgresql)
    implementation(libs.quarkus.hibernate.orm.panache.kotlin)
    implementation(libs.quarkus.flyway)
    implementation(libs.quarkus.flyway.postgresql)

    // Operations
    implementation(libs.quarkus.smallrye.health)

    testImplementation(libs.quarkus.junit)
    testImplementation(libs.rest.assured)
}

kotlin {
    jvmToolchain(25)
    compilerOptions {
        jvmTarget = JvmTarget.JVM_25
        // Keeps parameter names available for REST/CDI reflection.
        javaParameters = true
    }
}

// Quarkus proxies CDI beans and JPA entities, which requires non-final classes.
allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.enterprise.context.RequestScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
