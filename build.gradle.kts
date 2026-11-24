plugins {
    // Declared here once (not applied) so every module resolves the same plugin versions.
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.allopen) apply false
    alias(libs.plugins.kotlin.jpa) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.quarkus) apply false
    alias(libs.plugins.spotless)
}

spotless {
    val ktlintVersion = libs.versions.ktlint.get()
    kotlin {
        target("**/src/**/*.kt")
        targetExclude("**/build/**")
        ktlint(ktlintVersion)
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude("**/build/**")
        ktlint(ktlintVersion)
    }
}
