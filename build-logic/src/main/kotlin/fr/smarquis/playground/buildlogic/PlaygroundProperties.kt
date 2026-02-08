package fr.smarquis.playground.buildlogic

import fr.smarquis.playground.buildlogic.dsl.getByType
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import kotlin.reflect.KProperty

internal fun Project.playground() = PlaygroundProperties(this)
internal fun Project.libraries() = playground().libraries
internal val Project.libs get() = libraries()
internal fun Project.versions() = playground().versions
internal val Project.versions get() = versions()

internal fun <T> Project.getOrCreateExtra(
    key: String,
    create: (Project) -> T,
): T = extensions.extraProperties.run {
    @Suppress("UNCHECKED_CAST")
    (if (has(key)) get(key) as? T else null) ?: create(project).also { set(key, it) }
}

internal fun Project.getVersionsCatalog(): VersionCatalog = runCatching {
    extensions.getByType<VersionCatalogsExtension>().named("libs")
}.recoverCatching {
    throw IllegalStateException("No versions catalog found!", it)
}.getOrThrow()

internal class PlaygroundProperties private constructor(private val project: Project) {

    private val catalog by lazy(project::getVersionsCatalog)

    val libraries by lazy { PlaygroundLibraries(catalog) }
    val versions by lazy { PlaygroundVersions(catalog) }

    val warningsAsErrors: Boolean
        get() = project.providers.gradleProperty("playground.warningsAsErrors").toBoolean().getOrElse(true)
    val lintWarningsAsErrors: Boolean
        get() = project.providers.gradleProperty("playground.lint.warningsAsErrors").toBoolean().getOrElse(warningsAsErrors)

    private fun Provider<String>.toBoolean(): Provider<Boolean> = map(String::toBoolean)

    companion object {
        private const val EXT_KEY = "fr.smarquis.playground.PlaygroundProperties"
        operator fun invoke(project: Project): PlaygroundProperties =
            project.getOrCreateExtra(EXT_KEY, ::PlaygroundProperties)
    }

}

@Suppress("HasPlatformType")
internal class PlaygroundVersions(catalog: VersionCatalog) {

    val compileSdk by catalog
    val minSdk by catalog
    val targetSdk by catalog

    @Suppress("unused")
    private operator fun VersionCatalog.getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ) = findVersion(property.name).orElseThrow {
        IllegalStateException("Missing catalog version ${property.name}")
    }

}

@Suppress("HasPlatformType", "PropertyName")
internal class PlaygroundLibraries(catalog: VersionCatalog) {
    val `android-security-lint` by catalog
    val `android-desugarJdkLibs` by catalog
    val `kotlin-test` by catalog
    val `kotlin-test-junit` by catalog

    @Suppress("unused")
    private operator fun VersionCatalog.getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ) = findLibrary(property.name).orElseThrow {
        IllegalStateException("Missing catalog library ${property.name}")
    }
}
