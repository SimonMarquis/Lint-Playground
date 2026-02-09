package fr.smarquis.playground.buildlogic

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.Lint
import com.android.build.gradle.LintPlugin
import fr.smarquis.playground.buildlogic.dsl.apply
import fr.smarquis.playground.buildlogic.dsl.getByType
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Inspired by https://github.com/slackhq/foundry
 */
internal class PlaygroundLintPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        pluginManager.withPlugin("com.android.base") {
            configureDependencies()
            configureLintTask(extensions.getByType<CommonExtension>().lint)
        }
        pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
            apply<LintPlugin>()
            configureDependencies()
            configureLintTask(extensions.getByType())
        }
    }

    private fun Project.configureDependencies() {
        dependencies.add("lintChecks", libs.`android-security-lint`)
        dependencies.add("lintChecks", project(":checks"))
    }

    private fun Project.configureLintTask(
        lint: Lint,
        properties: PlaygroundProperties = playground(),
    ) = lint.apply {
        targetSdk = versions.targetSdk.toString().toInt()

        abortOnError = true
        warningsAsErrors = properties.lintWarningsAsErrors

        explainIssues = true
        textReport = true
        xmlReport = true
        htmlReport = true
        sarifReport = true

        checkDependencies = true
        checkReleaseBuilds = false
        absolutePaths = true

        enable += "StopShip"
        disable += "ObsoleteLintCustomCheck"

        lintConfig = isolated.rootProject.projectDirectory.file(".config/lint.xml").asFile
        baseline = isolated.rootProject.projectDirectory.file(".config/lint-baseline.xml").asFile
    }

}
