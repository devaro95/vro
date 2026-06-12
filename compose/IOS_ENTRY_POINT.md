# Step 4 — iOS Entry Point

## What was added

### `compose/src/iosMain/`

| File | Purpose |
|---|---|
| `VROIosApp.kt` | Entry point: creates a `UIViewController` via `ComposeUIViewController` |
| `VROIosNavigationScope.kt` | Scope passed to the content builder (bars state, start screen) |
| `navigator/VROIosComposableNavigator.kt` | iOS navigator backed by lambda callbacks |
| `utils/ScreenUtils.ios.kt` | Real `isTablet()` using `UIDevice.userInterfaceIdiom` |
| `initializers/VROIosScreenInitializer.kt` | Stepper, lifecycle, navigator, one-time listeners for iOS |
| `extensions/VROIosScreenExtensions.kt` | `VROIosScreen` composable — iOS equivalent of `vroComposableScreen` |

### `core-android/src/iosMain/`

| File | Purpose |
|---|---|
| `navigation/VROIosNavigator.kt` | Completed navigator with real callbacks replacing TODO stubs |

## How to use from Swift

```swift
import ComposeApp  // or your KMP framework name

@main
struct MyApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ContentView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        VROIosApp().rootViewController(
            startScreen: MyHomeScreen.self
        ) { scope in
            VROIosScreen(
                viewModel: MyHomeViewModel(),
                navigator: VROIosComposableNavigator(
                    onNavigate: { route in /* push route */ },
                    onBack: { _ in /* pop */ },
                    onFinish: { /* exit */ }
                ),
                content: MyHomeScreen.self
            )
        }
    }
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
```
