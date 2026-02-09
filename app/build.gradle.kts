plugins {
    alias(libs.plugins.playground.android.application)
    alias(libs.plugins.playground.lint)
}

dependencies {
    implementation(projects.libs.android)

    androidTestImplementation(libs.androidx.junit)
}
