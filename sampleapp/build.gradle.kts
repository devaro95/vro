plugins {
    `maven-publish`
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.sampleapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sampleapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":core"))
    implementation(project(":compose"))

    implementation(libs.material)
    implementation(libs.material3)
    implementation(libs.compose.activity)
    implementation(libs.koin.compose)
    implementation(libs.view.binding)
    implementation(libs.lottie.compose)
    implementation(libs.ui.tooling.preview)
    implementation(libs.junit)
    debugImplementation(libs.ui.tooling)
}