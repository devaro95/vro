plugins {
    kotlin("multiplatform")
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.vanniktech)
    alias(libs.plugins.nmcp)
    signing
    id("org.jetbrains.dokka") version "2.0.0"
}

group = property("GROUP") as String
version = property("VERSION_NAME") as String

apply(from = "../gradleConfig/configuration.gradle")

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core"))
                api(libs.coroutines.core)
                api(libs.koin.core)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.navigation.dynamic.features)
                api(libs.navigation.fragment)
                api(libs.navigation.compose)
                api(libs.arch.core)
                api(libs.compose.activity)
                api(libs.ui.tooling.preview)
                api(libs.mockito.inline)
                api(libs.mockito.core)
                api(libs.junit)
                api(libs.coroutines.test)
                api(libs.koin.compose)
                api(libs.navigation.ui)
                api(libs.lifecycle.viewmodel)
                api(libs.lifecycle.viewmodel.compose)
                implementation(libs.compose.runtime)
                implementation(libs.koin.android)
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
        }
        val iosX64Main by getting { dependsOn(iosMain) }
        val iosArm64Main by getting { dependsOn(iosMain) }
        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }
    }

    jvmToolchain(21)
}

android {
    compileSdk = 36
    defaultConfig {
        aarMetadata { minCompileSdk = 24 }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures { compose = true }
    namespace = "com.vro.coreandroid"
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    pom {
        name.set("Core Androidx Library")
        description.set("VRO Core Androidx Module")
        url.set("https://github.com/devaro95/vro")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("devaro95")
                name.set("Varo")
                email.set("alvaromcarmena95@gmail.com")
            }
        }
        scm {
            connection.set("scm:git:git://github.com/devaro95/vro.git")
            developerConnection.set("scm:git:ssh://github.com:devaro95/vro.git")
            url.set("https://github.com/devaro95/vro")
        }
    }
}
