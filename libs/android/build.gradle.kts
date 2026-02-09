plugins {
    alias(libs.plugins.playground.android.library)
    alias(libs.plugins.playground.lint)
}

dependencies {
    api(projects.libs.jvm)

    androidTestImplementation(libs.androidx.junit)
}
