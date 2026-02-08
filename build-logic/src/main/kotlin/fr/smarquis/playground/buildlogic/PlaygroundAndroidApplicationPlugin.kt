package fr.smarquis.playground.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AppPlugin
import fr.smarquis.playground.buildlogic.dsl.apply
import fr.smarquis.playground.buildlogic.dsl.configure
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class PlaygroundAndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply<AppPlugin>()
        apply<PlaygroundAndroidBasePlugin>()

        configure<ApplicationExtension> {
            defaultConfig {
                targetSdk = versions.targetSdk.toString().toInt()
            }
            buildTypes {
                debug {
                    applicationIdSuffix = ".debug"
                    isDebuggable = true
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
                }
            }
        }
    }
}
