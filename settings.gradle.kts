rootProject.name = "pms"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

plugins {
    // Lets Gradle download the JDK declared via `jvmToolchain(...)` when it is not installed locally.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

include(":shared")
include(":backend")
include(":composeApp")
