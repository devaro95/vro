plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
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
    debugImplementation(libs.ui.tooling)
}