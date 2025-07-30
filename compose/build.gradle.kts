plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.vanniktech.maven.publish") version "0.34.0"
    id("com.gradleup.nmcp") version "1.0.2" apply false
}

apply(from = "../gradleConfig/configuration.gradle")

android {
    compileSdk = 35

    defaultConfig {
        aarMetadata {
            minCompileSdk = 24
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_22.toString()
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

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.devaro95"
            version = "1.17.0"
            artifactId = "vro-compose"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
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