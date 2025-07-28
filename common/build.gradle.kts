plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("maven-publish")
    id("com.android.library")
}

apply(from = "../gradleConfig/configuration.gradle")

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting

        val iosMain by creating {
            dependsOn(commonMain)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }
}

android {
    compileSdk = 35
    namespace = "com.vro.common"
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
}

publishing {
    tasks.withType<PublishToMavenLocal> {
        dependsOn("assemble")
    }
    publications {
        withType<MavenPublication>().configureEach {
            groupId = "com.vro"
            version = System.getenv("GITHUB_REF")?.removePrefix("refs/tags/v") ?: "1.16.13"
        }

        named<MavenPublication>("kotlinMultiplatform") {
            artifactId = "vro-common"
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

