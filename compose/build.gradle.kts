plugins {
    kotlin("multiplatform")
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
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
                api(project(":core-android"))
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.components.resources)
                api(compose.animation)
                api(libs.koin.core)
                implementation(libs.coroutines.core)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.material3)
                api(libs.compose.icons.core)
                api(libs.compose.icons.extended)
                implementation(libs.accompanist.navigation.material)
                implementation(libs.navigation.compose)
                implementation(libs.lifecycle.runtime.compose.android)
                implementation(libs.compose.animation)
                implementation(libs.koin.compose)
                implementation(libs.compose.activity)
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
        aarMetadata {
            minCompileSdk = 24
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        compilerOptions {
            JavaVersion.VERSION_21.toString()
        }
    }
    buildFeatures {
        compose = true
    }
    namespace = "com.vro.compose"
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    pom {
        name.set("Common Library")
        description.set("VRO Common Module")
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
