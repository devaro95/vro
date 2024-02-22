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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    implementation(project(":app"))
    implementation(project(":core"))
    implementation(project(":compose"))

    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("io.insert-koin:koin-androidx-compose:3.3.0")
    implementation("com.airbnb.android:lottie-compose:4.0.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
}