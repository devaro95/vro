package com.vro.compose.screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vro.compose.composition.LocalBottomBarState
import com.vro.compose.composition.LocalTopBarState
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarState
import com.vro.constants.EMPTY_STRING
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.navigation.VROBackResult
import com.vro.state.VROState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

abstract class VROScreenContent<S : VROState, E : VROEvent> : KoinScopeComponent {

    lateinit var events: VROEventLauncher<E>

    override val scope: Scope by lazy { createScope(this) }

    open fun setTopBar(currentState: VROTopBarBaseState, isScreenStarted: Boolean): VROTopBarBaseState = VROTopBarStartState()

    open fun setBottomBar(currentState: VROBottomBarBaseState, isScreenStarted: Boolean): VROBottomBarBaseState = VROBottomBarStartState()

    @Composable
    abstract fun Content(state: S)

    @VROLightMultiDevicePreview
    @Composable
    abstract fun ScreenPreview()

    lateinit var coroutineScope: CoroutineScope

    fun showSnackbar(
        state: MutableState<VROSnackBarState>,
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
            state.value = VROSnackBarState(
                hostState = state.value.hostState,
                textColor = textColor,
                backgroundColor = backgroundColor,
                actionColor = actionColor
            )
            val snackResult = state.value.hostState.showSnackbar(
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

    fun event(event: E) {
        events.doEvent(event)
    }

    fun navigateBack(result: VROBackResult? = null) {
        events.doBack(result)
    }

    @Composable
    fun UpdateTopBar(changeStateFunction: VROTopBarState.() -> VROTopBarState) {
        val navController = rememberNavController()
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        val topBarState = LocalTopBarState.current
        LaunchedEffect(currentDestination, changeStateFunction) {
            val currentState = topBarState.value as? VROTopBarState
            if (currentState != null) {
                topBarState.value = changeStateFunction(currentState)
            } else {
                topBarState.value = VROTopBarStartState()
            }
        }
    }

    @Composable
    fun UpdateBottomBar(changeStateFunction: VROBottomBarState.() -> VROBottomBarState) {
        val navController = rememberNavController()
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        val bottomBarState = LocalBottomBarState.current
        LaunchedEffect(currentDestination, changeStateFunction) {
            val currentState = bottomBarState.value as? VROBottomBarState
            if (currentState != null) {
                bottomBarState.value = changeStateFunction(currentState)
            } else {
                bottomBarState.value = VROBottomBarStartState()
            }
        }
    }
}
