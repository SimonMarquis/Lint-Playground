import org.gradle.api.JavaVersion.VERSION_21
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.sam)
    alias(libs.plugins.kotlin.assignment)
    alias(libs.plugins.android.lint)
    `java-gradle-plugin`
}

group = "fr.smarquis.playground.buildlogic"

java {
    sourceCompatibility = VERSION_21
    targetCompatibility = VERSION_21
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = providers.gradleProperty("playground.warningsAsErrors").map(String::toBoolean).getOrElse(true)
        jvmTarget = JVM_21
    }
    explicitApi()
}

samWithReceiver {
    annotation(HasImplicitReceiver::class.qualifiedName!!)
}
assignment {
    annotation(SupportsKotlinAssignmentOverloading::class.qualifiedName!!)
}

dependencies {
    compileOnly(plugin(libs.plugins.android.cacheFix))
    compileOnly(plugin(libs.plugins.develocity))
    compileOnly(plugin(libs.plugins.kotlin.jvm))

    compileOnly(libs.android.gradle.api)
    compileOnly(libs.android.tools.common)
    lintChecks(libs.androidx.lint.gradle)
}

private fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>) = plugin.map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version.requiredVersion}"
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        create("PlaygroundAndroidApplicationPlugin", libs.plugins.playground.android.application)
        create("PlaygroundAndroidLibraryPlugin", libs.plugins.playground.android.library)
        create("PlaygroundAndroidTestPlugin", libs.plugins.playground.android.test)
        create("PlaygroundKotlinJvmPlugin", libs.plugins.playground.kotlin.jvm)
        create("PlaygroundLintPlugin", libs.plugins.playground.lint)
    }
}

private fun NamedDomainObjectContainer<PluginDeclaration>.create(
    name: String,
    plugin: Provider<PluginDependency>,
) = register(name) {
    id = plugin.get().pluginId
    implementationClass = "fr.smarquis.playground.buildlogic.$name"
}
