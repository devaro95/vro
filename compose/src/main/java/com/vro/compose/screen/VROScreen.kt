package com.vro.compose.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROSnackBarState
import com.vro.compose.states.VROTopBarBaseState
import com.vro.compose.utils.isTablet
import com.vro.event.VROEvent
import com.vro.state.VROState

/**
 * Abstract base class for Compose screens in the application.
 * Provides shared functionality for screen-level features such as:
 * - App bar (top and bottom) configuration
 * - Snackbar handling
 * - Reactive state and event management
 *
 * Subclasses are expected to define screen content and customize UI behavior
 * through [screenContent] and associated handlers.
 *
 * @param S The UI state type, extending [VROState]
 * @param E The event type, extending [VROEvent]
 *
 * @see VROScreenBase for the base implementation
 */
abstract class VROScreen<S : VROState, E : VROEvent> : VROScreenBase<S, E>() {

    abstract val screenContent: VROScreenContent<S, E>

    /**
     * Initializes the scaffold bars (top and bottom) by applying the configuration
     * returned from [screenContent]'s `setTopBar` and `setBottomBar` methods.
     *
     * This is invoked automatically during screen setup and should not be called directly.
     *
     * @param topBarState A mutable state holding the top app bar configuration
     * @param bottomBarState A mutable state holding the bottom app bar configuration
     *
     * @see VROTopBarBaseState for top bar structure
     * @see VROBottomBarBaseState for bottom bar structure
     */
    internal fun configureScaffold(
        topBarState: MutableState<VROTopBarBaseState>,
        bottomBarState: MutableState<VROBottomBarBaseState>,
    ) {
        topBarState.value = screenContent.setTopBar(topBarState.value)
        bottomBarState.value = screenContent.setBottomBar(bottomBarState.value)
    }

    @Composable
    override fun InitializeContent(
        state: S,
        topBarState: MutableState<VROTopBarBaseState>,
        bottomBarState: MutableState<VROBottomBarBaseState>,
        snackbarState: MutableState<VROSnackBarState>,
    ) {
        this.screenContent.events = events
        this.screenContent.topBarState = topBarState
        this.screenContent.bottomBarState = bottomBarState
        screenContent.coroutineScope = rememberCoroutineScope()
        screenContent.snackbarState = snackbarState
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isTablet() && tabletDesignEnabled) {
                TabletContent(state)
            } else {
                screenContent.Content(state)
            }
        }
    }
}