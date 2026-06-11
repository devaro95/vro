# Compose module — KMP source layout

This module is now Kotlin Multiplatform.

## Source sets

- `commonMain` — UI logic, screens, handlers, states, theme, skeleton (Compose Multiplatform)
- `androidMain` — Activity, Fragment, Android-specific initializers, VROComposableNavigator
- `iosMain` — iOS entry points (populated in Step 4)

## Old source location

The original `src/main/java/` sources are kept temporarily for reference and will be removed
once the new sourcesets are verified to compile correctly.
