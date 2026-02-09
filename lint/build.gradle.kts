plugins {
    alias(libs.plugins.playground.android.library)
    alias(libs.plugins.playground.lint)
}

dependencies {
    lintPublish(projects.checks) {
        // Remove automatic api dependency on kotlin-stdlib to be able to publish `:lint` module.
        // Lint library only supports a single JAR file in the 'lintPublish' configuration.
        // And `kotlin.stdlib.default.dependency=false` applies to the entire project.
        isTransitive = false
    }
}
