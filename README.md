![alt text](https://github.com/devaro95/vro/blob/master/header.jpg)

[![](https://jitpack.io/v/devaro95/vro.svg)](https://jitpack.io/#devaro95/vro)
# 👨‍💻 VRO 
Vro is a framework, based on a MVI architecture and focused on the use of States, fully created to keep a full clean architecture and to make the creation of Android projects easier. 

# :rocket: First Steps 
Implement the VRO dependency directly to your project using Jitpack repository.

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add VRO compose dependency to your presentation project module (Android Jetpack Compose Project):
```gradle
dependencies {
    implementation("com.github.devaro95:vro-compose:{$VROVersion}")
}
```
Add VRO core dependency to your core project module:
```gradle
dependencies {
    implementation("com.github.devaro95:vro-core:{$VROVersion}")
}
```
# 📚  Learn More
Take a look at the initiation guide on the repository's wiki.

[VRO Wiki](https://github.com/devaro95/vro/wiki)
