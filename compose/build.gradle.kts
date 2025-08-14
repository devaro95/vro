plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.vanniktech)
    alias(libs.plugins.nmcp)
    signing
    id("org.jetbrains.dokka") version "2.0.0"
}

group = property("GROUP") as String
version = property("VERSION_NAME") as String

apply(from = "../gradleConfig/configuration.gradle")

android {
    compileSdk = 35

    defaultConfig {
        aarMetadata {
            minCompileSdk = 24
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
    buildFeatures {
        compose = true
    }
    namespace = "com.vro.compose"
}

dependencies {
    api(project(":core-android"))
    api(libs.material3)
    implementation(libs.accompanist.navigation.material)
    implementation(libs.lifecycle.runtime.compose.android)
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