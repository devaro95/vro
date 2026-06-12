package com.vro.compose

import androidx.compose.runtime.*
import androidx.compose.ui.window.ComposeUIViewController
import com.vro.compose.screen.VROScreenBase
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState
import com.vro.compose.theme.VROComposableCustomTheme
import platform.UIKit.UIViewController
import kotlin.reflect.KClass

/**
 * iOS entry point for VRO apps.
 *
 * Usage from Swift:
 * ```swift
 * let controller = VROIosApp().rootViewController(
 *     startScreen = MyScreen::class
 * )
 * ```
 */
class VROIosApp(
    open val customTheme: VROComposableCustomTheme? = null,
) {
    fun rootViewController(
        startScreen: KClass<out VROScreenBase<*, *>>,
        createContent: @Composable VROIosNavigationScope.() -> Unit,
    ): UIViewController = ComposeUIViewController {
        val topBarState = remember { mutableStateOf<VROTopBarBaseState>(VROTopBarStartState()) }
        val bottomBarState = remember { mutableStateOf<VROBottomBarBaseState>(VROBottomBarStartState()) }
        val scope = VROIosNavigationScope(
            startScreen = startScreen,
            topBarState = topBarState,
            bottomBarState = bottomBarState,
        )
        scope.createContent()
    }
}
