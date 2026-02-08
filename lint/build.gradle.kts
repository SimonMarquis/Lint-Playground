import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.android.lint)
}

// Compile lint rules when IDE sync to always have up-to-date rules.
tasks.maybeCreate("prepareKotlinIdeaImport")
    .dependsOn(tasks.assemble)

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    explicitApi()
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    compileOnly(libs.lint.api)

    testImplementation(kotlin("test"))
    testImplementation(libs.lint.checks)
    testImplementation(libs.lint.tests)
}
