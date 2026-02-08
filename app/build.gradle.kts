plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "fr.smarquis.playground.lint"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "fr.smarquis.playground.lint"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    lint {
        checkDependencies = true
        abortOnError = true
        lintConfig = isolated.rootProject.projectDirectory.file(".config/lint.xml").asFile
        textReport = true
        xmlReport = true
        sarifReport = true
        htmlReport = true
    }
}

dependencies {
    lintChecks(projects.lint)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}
