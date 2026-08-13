import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.playground.kotlin.jvm)
    alias(libs.plugins.playground.lint)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin.compilerOptions.jvmTarget = JvmTarget.JVM_17

// Compile lint rules when IDE sync to always have up-to-date rules.
tasks.maybeCreate("prepareKotlinIdeaImport")
    .dependsOn(tasks.assemble)

dependencies {
    compileOnly(libs.lint.api)

    testImplementation(libs.lint.checks)
    testImplementation(libs.lint.tests)
}
