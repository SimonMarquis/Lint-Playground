plugins {
    alias(libs.plugins.playground.android.library)
    alias(libs.plugins.playground.lint)
}

dependencies {
    api(project(":libs:jvm"))

    androidTestImplementation(libs.androidx.junit)
}
