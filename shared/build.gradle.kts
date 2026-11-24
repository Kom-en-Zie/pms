import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

/*
 * Code shared between the backend (JVM) and every client (web now; Android/iOS later).
 *
 * Put here: API contracts (DTOs), API route constants, domain enums/value types,
 * validation rules and pure business logic. Never put framework-specific code here
 * (no Quarkus, no Compose, no Ktor) so it stays usable on every target.
 */
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvmToolchain(25)

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    // Future clients: add `androidLibrary { ... }` and `iosArm64()` / `iosSimulatorArm64()`
    // here (see docs/adr/0002-kotlin-multiplatform-code-sharing.md).

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.serialization.json)
            api(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
