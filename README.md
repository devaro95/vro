![alt text](https://github.com/devaro95/vro/blob/master/header.jpg)

[![Maven Central](https://img.shields.io/maven-central/v/io.github.devaro95.vro/vro-core?color=blue&style=flat-square&logo=apachemaven)](https://search.maven.org/search?q=g:io.github.devaro95.vro)

> [!NOTE]
VRO is based on [EMA](https://github.com/carmabs/ema), an Android MVI architecture, developed by [carmabs](https://github.com/carmabs).
# 👨‍💻 Welcome to VRO 
Vro is a framework architecture, based on a MVI architecture and focused on the use of States, fully created to keep a full clean architecture and to make the creation of Android projects easier. 

# :rocket: First Steps 
Implement the VRO dependency directly to your project using Jitpack repository.

```gradle
allprojects {
    repositories {
        ...
        mavenCentral()
    }
}
```
Add VRO compose dependency to your presentation project module (Android Jetpack Compose Project):
```gradle
//Using libs.version file:
vro-compose = { group = "com.github.devaro95.vro", name = "vro-compose", version.ref = "vroVersion" }

//Using old mode:
dependencies {
    implementation("com.github.devaro95:vro-compose:{$VROVersion}")
}
```
Add VRO core dependency to your core project module:
```gradle
//Using libs.version file:
vro-core = { group = "com.github.devaro95.vro", name = "vro-core", version.ref = "vroVersion" }

//Using old mode:
dependencies {
    implementation("com.github.devaro95:vro-core:{$VROVersion}")
}
```
# 📚  Learn More
Take a look at the initiation guide on the repository's wiki.

[VRO Wiki](https://github.com/devaro95/vro/wiki)
