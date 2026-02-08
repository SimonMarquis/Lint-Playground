plugins {
    alias(libs.plugins.playground.android.library)
    alias(libs.plugins.playground.lint)
}

dependencies {
    androidTestImplementation(libs.androidx.junit)
}
