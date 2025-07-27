plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("maven-publish")
    id("com.android.library")
}

apply(from = "../gradleConfig/configuration.gradle")

kotlin {
    // Targets
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_21.toString()
        }
    }
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Aquí pones las dependencias compartidas
                implementation(libs.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val jvmMain by getting
        val jvmTest by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

publishing {
    publications {
        withType<MavenPublication>().all {
            groupId = "com.vro"
            artifactId = "vro-common"
            version = "1.16.3"
        }
    }
}

