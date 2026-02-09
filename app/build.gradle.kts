plugins {
    alias(libs.plugins.playground.android.application)
    alias(libs.plugins.playground.lint)
}

dependencies {
    implementation(projects.libs.android)
    implementation(projects.libs.jvm)

    androidTestImplementation(libs.androidx.junit)
}
