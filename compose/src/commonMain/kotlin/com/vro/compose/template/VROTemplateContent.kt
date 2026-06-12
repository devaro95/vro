package com.vro.compose.template

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
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

abstract class VROTemplateContent<S : VROState, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> : KoinScopeComponent {

    lateinit var events: VROEventLauncher<E>
    override val scope: Scope by lazy { createScope(this) }
    internal lateinit var topBarState: MutableState<VROTopBarBaseState>
    internal lateinit var bottomBarState: MutableState<VROBottomBarBaseState>
    abstract val mapper: M
    abstract fun render(state: S): R

    open fun setTopBar(currentState: VROTopBarBaseState): VROTopBarBaseState = VROTopBarStartState()
    open fun setBottomBar(currentState: VROBottomBarBaseState): VROBottomBarBaseState = VROBottomBarStartState()

    @Composable
    abstract fun Content(state: S)

    @Composable
    abstract fun ScreenPreview()

    lateinit var coroutineScope: CoroutineScope
    lateinit var snackbarState: MutableState<VROSnackBarState>

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

    fun event(event: E) = events.doEvent(event)
    fun navigateBack(result: VROBackResult? = null) = events.doBack(result)

    @Composable
    fun UpdateTopBar(changeStateFunction: VROTopBarState.() -> VROTopBarState) {
        LaunchedEffect(changeStateFunction) {
            (topBarState.value as? VROTopBarState)?.let { topBarState.value = changeStateFunction(it) }
        }
    }

    @Composable
    fun UpdateBottomBar(changeStateFunction: VROBottomBarState.() -> VROBottomBarState) {
        LaunchedEffect(changeStateFunction) {
            (bottomBarState.value as? VROBottomBarState)?.let { bottomBarState.value = changeStateFunction(it) }
        }
    }
}
