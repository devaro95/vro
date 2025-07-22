plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("maven-publish")
    id("com.android.library")
}

apply(from = "../gradleConfig/configuration.gradle")

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_21.toString()
            kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
        }
    }
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting{
            dependencies {
                api(project(":common"))
                implementation(libs.coroutines.core)
                implementation(libs.koin.annotations)
                implementation(libs.koin.core)
            }
        }
        val commonTest by getting

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

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting

        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 35
    namespace = "com.vro.core"

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
            artifactId = "vro-core"
            version = "1.16.3"
        }
    }
}
