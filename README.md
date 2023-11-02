[![](https://jitpack.io/v/devaro95/vro.svg)](https://jitpack.io/#devaro95/vro)
# VRO
Vro is a framework create to facilitate the creation of Android projects. Based on a MVI architecture and focused on the use of States, fully created to keep a full clean architecture.

# First Steps
Implement the Vro dependency directly to your project using Jitpack repository.

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add Vro dependencies to your project
```
dependencies {
    implementation("com.github.devaro95:vro:{$VroVersion}")
}
```
