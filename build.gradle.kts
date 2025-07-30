plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.jetbrains.jvm) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("com.vanniktech.maven.publish") version "0.34.0" apply false
    id("com.gradleup.nmcp") version "1.0.2" apply false
}