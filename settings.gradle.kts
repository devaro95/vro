pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://nexus.ods.ok-cloud.net/repository/mb-android-group/")
    }
}

rootProject.name = "vro"
include(":app")
include(":sampleapp")
include(":core")
include(":compose")
include(":common")
