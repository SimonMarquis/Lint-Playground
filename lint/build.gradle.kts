plugins {
    alias(libs.plugins.playground.kotlin.jvm)
    alias(libs.plugins.playground.lint)
}

// Compile lint rules when IDE sync to always have up-to-date rules.
tasks.maybeCreate("prepareKotlinIdeaImport")
    .dependsOn(tasks.assemble)

dependencies {
    compileOnly(libs.lint.api)

    testImplementation(libs.lint.checks)
    testImplementation(libs.lint.tests)
}
