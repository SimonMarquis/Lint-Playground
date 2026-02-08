package fr.smarquis.playground.buildlogic

import com.android.build.api.dsl.CommonExtension
import fr.smarquis.playground.buildlogic.dsl.apply
import fr.smarquis.playground.buildlogic.dsl.configure
import org.gradle.android.AndroidCacheFixPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal class PlaygroundAndroidBasePlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        apply<AndroidCacheFixPlugin>()
        configureKotlin<KotlinAndroidProjectExtension>()

        configure<CommonExtension> {
            val parts = path.split("""\W""".toRegex()).drop(1).distinct().dropLastWhile { it == "impl" }
            namespace = "fr.smarquis.playground.lint." + parts.joinToString(separator = ".").lowercase()
            resourcePrefix = "playground_" + parts.joinToString(separator = "_").lowercase() + "_"

            compileSdk {
                version = release(versions.compileSdk.toString().toInt())
            }

            defaultConfig.apply {
                minSdk = versions.minSdk.toString().toInt()
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            testOptions.animationsDisabled = true

            packaging.resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }

            compileOptions.apply {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
                isCoreLibraryDesugaringEnabled = true
            }
        }

        dependencies.add("testImplementation", libs.`kotlin-test-junit`)
        dependencies.add("coreLibraryDesugaring", libs.`android-desugarJdkLibs`.get())
    }

}
