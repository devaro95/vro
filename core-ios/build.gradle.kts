plugins {
    kotlin("multiplatform")
    alias(libs.plugins.vanniktech)
    alias(libs.plugins.nmcp)
    signing
}

group = property("GROUP") as String
version = property("VERSION_NAME") as String

apply(from = "../gradleConfig/configuration.gradle")

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":core"))
                implementation(libs.coroutines.core)
            }
        }
        val iosMain by creating { dependsOn(commonMain) }
        val iosX64Main by getting { dependsOn(iosMain) }
        val iosArm64Main by getting { dependsOn(iosMain) }
        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }
    }

    jvmToolchain(21)
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    pom {
        name.set("Core iOS Library")
        description.set("VRO Core iOS Module")
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
