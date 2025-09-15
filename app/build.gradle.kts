plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.vanniktech)
    signing
}

apply (from= "../gradleConfig/configuration.gradle")

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
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
    buildFeatures {
        compose = true
    }
    namespace = "com.vro.app"
}

dependencies {
    api(project(":core"))
    api(project(":core-android"))

    api(libs.material3)
    api(libs.view.binding)
    implementation(libs.arch.core)
    implementation(libs.core)
    implementation(libs.app.compat)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.material3.size)
    implementation(libs.retrofit)
    implementation(libs.retrofit.scalars)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.coroutines)
    implementation(libs.logging.interceptor)

    debugImplementation(libs.ui.tooling)
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    pom {
        name.set("Legacy Library")
        description.set("VRO Legacy Module")
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