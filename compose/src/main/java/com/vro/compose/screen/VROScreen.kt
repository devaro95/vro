package com.vro.compose.screen

import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarState
import com.vro.constants.EMPTY_STRING
import com.vro.event.VROEvent
import com.vro.state.VROState
import kotlinx.coroutines.launch

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
abstract class VROScreen<S : VROState, E : VROEvent> : VROScreenBase<S, E>() {

    /**
     * The Android context for the screen. Must be initialized before use.
     */
    lateinit var context: Context

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
        topBarState.value = setTopBar(topBarState.value)
        bottomBarState.value = setBottomBar(bottomBarState.value)
    }

    /**
     * Updates the top app bar state in response to navigation changes.
     * The provided lambda receives the current state and returns a modified version.
     *
     * @param changeStateFunction Lambda that transforms the current top bar state
     *
     * @sample com.vro.compose.screen.TopBarUpdateSample
     */
    @Composable
    fun UpdateTopBar(changeStateFunction: VROTopBarState.() -> VROTopBarState) {
        val navController = rememberNavController()
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        LaunchedEffect(currentDestination) {
            (topBarState.value as? VROTopBarState)?.let {
                topBarState.value = changeStateFunction.invoke(it)
            }
        }
    }

    /**
     * Updates the bottom app bar state in response to navigation changes.
     * The provided lambda receives the current state and returns a modified version.
     *
     * @param changeStateFunction Lambda that transforms the current bottom bar state
     *
     * @sample com.vro.compose.screen.BottomBarUpdateSample
     */
    @Composable
    fun UpdateBottomBar(changeStateFunction: VROBottomBarState.() -> VROBottomBarState) {
        val navController = rememberNavController()
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        LaunchedEffect(currentDestination) {
            (bottomBarState.value as? VROBottomBarState)?.let {
                bottomBarState.value = changeStateFunction.invoke(it)
            }
        }
    }

    /**
     * Shows a snackbar with customizable appearance and behavior.
     *
     * @param message The text message to display
     * @param actionLabel The label for the action button (empty string hides the button)
     * @param duration How long to display the snackbar [SnackbarDuration.Short/Long/Indefinite]
     * @param textColor Color of the message text (default: 0xFF1C1B1F)
     * @param backgroundColor Background color (default: 0xFFFFFFFF)
     * @param actionColor Color of the action button text (default: 0xFF6750A4)
     * @param onAction Callback when the action button is clicked
     * @param onDismiss Callback when the snackbar is dismissed
     *
     * @throws IllegalStateException if called before snackbarState is initialized
     * @sample com.vro.compose.screen.SnackbarSample
     */
    fun showSnackbar(
        message: String,
        actionLabel: String = EMPTY_STRING,
        duration: SnackbarDuration = SnackbarDuration.Short,
        textColor: Color = Color(0xFF1C1B1F),
        backgroundColor: Color = Color(0xFFFFFFFF),
        actionColor: Color = Color(0xFF6750A4),
        onAction: () -> Unit = {},
        onDismiss: () -> Unit = {},
    ) {
        coroutineScope.launch {
            snackbarState.value = VROSnackBarState(
                snackbarState.value.hostState,
                textColor,
                backgroundColor,
                actionColor
            )
            val snackResult = snackbarState.value.hostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = duration,
            )
            when (snackResult) {
                SnackbarResult.ActionPerformed -> onAction()
                SnackbarResult.Dismissed -> onDismiss()
            }
        }
    }
}