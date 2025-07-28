plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("com.android.library")
}

apply(from = "../gradleConfig/configuration.gradle")

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":common"))
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                implementation(libs.coroutines.core)
                implementation(libs.koin.annotations)
                implementation(libs.koin.core)
            }
        }
        val androidMain by getting {
            dependencies {

            }
        }
        val iosMain by creating { dependsOn(commonMain) }
        val iosArm64Main by getting { dependsOn(iosMain) }
        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }
    }
}

android {
    compileSdk = 35
    namespace = "com.vro.core"

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
}

publishing {
    publications {
        withType<MavenPublication>().configureEach {
            groupId = "com.vro"
            version = System.getenv("GITHUB_REF")?.removePrefix("refs/tags/v") ?: "1.16.13"
        }

        named<MavenPublication>("kotlinMultiplatform") {
            artifactId = "vro-core"
        }

    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/devaro95/vro")
            credentials {
                username = project.findProperty("github.user") as? String ?: System.getenv("GITHUB_USER") ?: ""
                password = project.findProperty("github.token") as? String ?: System.getenv("GITHUB_TOKEN") ?: ""
            }
        }
    }
}