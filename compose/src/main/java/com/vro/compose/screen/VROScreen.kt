package com.vro.compose.screen

import androidx.compose.runtime.MutableState
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROTopBarBaseState
import com.vro.event.VROEvent
import com.vro.state.VROState

/**
 * Abstract base class for Compose screens in the application.
 * Provides common functionality for screen management including:
 * - App bar configuration (top/bottom)
 * - Snackbar presentation
 * - State management
 *
 * @param S The state type that extends [VROState]
 * @param E The event type that extends [VROEvent]
 *
 * @see VROScreenBase for the parent class implementation
 */
abstract class VROScreen<S : VROState, E : VROEvent>() : VROScreenBase<S, E>() {

    /**
     * Configures the scaffold with initial top and bottom bar states.
     * Called internally during screen initialization.
     *
     * @param topBarState Mutable state for the top app bar configuration
     * @param bottomBarState Mutable state for the bottom app bar configuration
     *
     * @see setTopBar for top bar customization
     * @see setBottomBar for bottom bar customization
     */
    internal fun configureScaffold(
        topBarState: MutableState<VROTopBarBaseState>,
        bottomBarState: MutableState<VROBottomBarBaseState>,
    ) {
        topBarState.value = screenContent.setTopBar(topBarState.value)
        bottomBarState.value = screenContent.setBottomBar(bottomBarState.value)
    }
}