package com.vro.compose.screen

import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarState
import com.vro.compose.states.VROSnackBarState
import com.vro.compose.states.VROTopBarBaseState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarState
import com.vro.constants.EMPTY_STRING
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.navigation.VROBackResult
import com.vro.state.VROState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class VROScreenContent<S : VROState, E : VROEvent> {

    /**
     * The Android context for the screen. Must be initialized before use.
     */
    lateinit var context: Context

    lateinit var events: VROEventLauncher<E>

    /**
     * Mutable state for top app bar configuration.
     */
    internal lateinit var topBarState: MutableState<VROTopBarBaseState>

    /**
     * Mutable state for bottom app bar configuration.
     */
    internal lateinit var bottomBarState: MutableState<VROBottomBarBaseState>

    /**
     * Configures the initial state of the top app bar.
     * Override to provide custom top bar configurations.
     *
     * @param currentState The current top bar state
     * @return The desired top bar configuration state
     */
    open fun setTopBar(currentState: VROTopBarBaseState): VROTopBarBaseState = VROTopBarStartState()

    /**
     * Configures the initial state of the bottom app bar.
     * Override to provide custom bottom bar configurations.
     *
     * @param currentState The current bottom bar state
     * @return The desired bottom bar configuration state
     */
    open fun setBottomBar(currentState: VROBottomBarBaseState): VROBottomBarBaseState = VROBottomBarStartState()

    @Composable
    abstract fun Content(state: S)

    /**
     * Abstract composable function that must be implemented to provide a preview of the screen.
     * Annotated with [VROLightMultiDevicePreview] for multi-device preview support.
     */
    @VROLightMultiDevicePreview
    @Composable
    abstract fun ScreenPreview()

    /**
     * Coroutine scope tied to the screen's lifecycle.
     */
    lateinit var coroutineScope: CoroutineScope

    /**
     * Mutable state for snackbar presentation.
     */
    lateinit var snackbarState: MutableState<VROSnackBarState>

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
                hostState = snackbarState.value.hostState,
                textColor = textColor,
                backgroundColor = backgroundColor,
                actionColor = actionColor
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

    /**
     * Dispatches an event to the ViewModel.
     *
     * @param event The event to dispatch
     */
    fun event(event: E) {
        events.doEvent(event)
    }

    /**
     * Navigates back with an optional result.
     *
     * @param result Optional result to pass back to the previous screen
     */
    fun navigateBack(result: VROBackResult? = null) {
        events.doBack(result)
    }

    /**
     * Updates the top app bar state in response to navigation changes.
     * The provided lambda receives the current state and returns a modified version.
     *
     * @param changeStateFunction Lambda that transforms the current top bar state
     *
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
}