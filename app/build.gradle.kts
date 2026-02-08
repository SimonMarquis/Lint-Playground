plugins {
    alias(libs.plugins.playground.android.application)
}

dependencies {
    lintChecks(projects.lint)

    implementation(projects.libs.android)
    implementation(projects.libs.jvm)

    androidTestImplementation(libs.androidx.junit)
}
