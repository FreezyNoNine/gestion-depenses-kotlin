pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        google()
        maven { url = uri("https://jitpack.io") }
    }
}

// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        google() // Required for Android libraries
        mavenCentral() // Required for Kotlin libraries
        maven { url = uri("https://jitpack.io") } // If you need JitPack
    }
}

rootProject.name = "GestionDepenses"
include(":app")
