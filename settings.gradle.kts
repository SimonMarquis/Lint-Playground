pluginManagement {
    includeBuild("build-logic")
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
    }
}

plugins {
    id("com.gradle.develocity") version "4.3.2"
    id("com.gradle.common-custom-user-data-gradle-plugin") version "2.4.0"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

develocity {
    buildScan {
        termsOfUseUrl = "https://gradle.com/terms-of-service"
        termsOfUseAgree = "yes"
        val isCi = providers.environmentVariable("CI").map(String::toBoolean)
        publishing.onlyIf { isCi.getOrElse(false) }
        uploadInBackground = isCi
        obfuscation {
            ipAddresses { it.map { "0.0.0.0" } }
        }
    }
}

// https://docs.gradle.org/current/userguide/configuration_cache.html#config_cache:stable
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Lint-Playground"

include(":app")
include(":lint")
include(":libs:android")
include(":libs:jvm")
