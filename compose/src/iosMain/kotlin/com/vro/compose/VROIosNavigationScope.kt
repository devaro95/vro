package com.vro.compose

import androidx.compose.runtime.MutableState
import com.vro.compose.screen.VROScreenBase
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROTopBarBaseState
import kotlin.reflect.KClass

/**
 * Scope passed to the iOS content builder.
 * Provides access to scaffold state and start screen reference.
 */
class VROIosNavigationScope(
    val startScreen: KClass<out VROScreenBase<*, *>>,
    val topBarState: MutableState<VROTopBarBaseState>,
    val bottomBarState: MutableState<VROBottomBarBaseState>,
)
