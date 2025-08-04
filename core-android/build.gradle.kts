plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.vanniktech)
    alias(libs.plugins.nmcp)
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
    namespace = "com.vro.coreandroid"
}

dependencies {
    api(project(":core"))
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
    implementation(libs.compose.runtime)
    implementation(libs.koin.android)
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