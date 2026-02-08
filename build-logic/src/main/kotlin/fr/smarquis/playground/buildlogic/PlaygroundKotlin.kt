package fr.smarquis.playground.buildlogic

import fr.smarquis.playground.buildlogic.dsl.assign
import fr.smarquis.playground.buildlogic.dsl.configure
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension


internal inline fun <reified T : KotlinBaseExtension> Project.configureKotlin(
    properties: PlaygroundProperties = playground(),
    crossinline configure: T.() -> Unit = {},
) {
    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    configure<T> {
        val kotlin = when (this) {
            is KotlinAndroidProjectExtension -> this
            is KotlinJvmProjectExtension -> this
            else -> TODO("Unsupported project extension $this ${T::class}")
        }
        kotlin.compilerOptions {
            jvmTarget = JvmTarget.JVM_11
            allWarningsAsErrors = properties.warningsAsErrors
        }
        explicitApi()
        configure()
    }
}
