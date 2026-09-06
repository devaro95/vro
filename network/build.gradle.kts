plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.vanniktech)
    alias(libs.plugins.nmcp)
    signing
}

group = property("GROUP") as String
version = property("VERSION_NAME") as String

apply(from = "../gradleConfig/configuration.gradle")

android {
    compileSdk = 36
    namespace = "com.vro.network"

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
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.scalars)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.coroutines)
    implementation(libs.logging.interceptor)
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    pom {
        name.set("Network Library")
        description.set("VRO Network Module")
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
