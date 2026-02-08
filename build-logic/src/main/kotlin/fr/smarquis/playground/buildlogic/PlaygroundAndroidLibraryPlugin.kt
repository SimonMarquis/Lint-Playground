package fr.smarquis.playground.buildlogic

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import fr.smarquis.playground.buildlogic.dsl.apply
import fr.smarquis.playground.buildlogic.dsl.configure
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class PlaygroundAndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply<LibraryPlugin>()
        apply<PlaygroundAndroidBasePlugin>()
        configure<LibraryExtension> {
            defaultConfig {
                aarMetadata.minCompileSdk = versions.minSdk.toString().toInt()
            }
        }
    }

}
