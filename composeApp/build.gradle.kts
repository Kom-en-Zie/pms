import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

/*
 * The client application: UI, navigation, view models and the API client.
 *
 * Almost everything belongs in `commonMain` so it runs unchanged on every target.
 * Platform source sets (`wasmJsMain`, later `androidMain` / `iosMain`) only hold
 * entry points and the few platform-specific `actual` implementations.
 */
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    // Future clients: add `androidTarget()` / `iosArm64()` / `iosSimulatorArm64()` here
    // (see docs/adr/0002-kotlin-multiplatform-code-sharing.md).

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared)

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.material3)
            implementation(libs.compose.material3.adaptive)
            implementation(libs.compose.material3.adaptive.navigation.suite)

            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

compose.resources {
    packageOfResClass = "nl.komenzie.pms.app.resources"
}
