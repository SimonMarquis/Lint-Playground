plugins {
    alias(libs.plugins.playground.kotlin.jvm)
    alias(libs.plugins.playground.lint)
}

dependencies {
    api(projects.libs.annotations)
}
