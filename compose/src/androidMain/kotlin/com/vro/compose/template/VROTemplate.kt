package com.vro.compose.template

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.vro.compose.composition.LocalBottomBarState
import com.vro.compose.composition.LocalTopBarState
import com.vro.compose.screen.VROScreenBase
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROTopBarBaseState
import com.vro.compose.utils.isTablet
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.state.VROState

abstract class VROTemplate<S : VROState, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> :
    VROScreenBase<S, E>() {

    abstract val templateContent: VROTemplateContent<S, E, M, R>

    internal fun configureScaffold(
        topBarState: MutableState<VROTopBarBaseState>,
        bottomBarState: MutableState<VROBottomBarBaseState>,
    ) {
        topBarState.value = templateContent.setTopBar(topBarState.value)
        bottomBarState.value = templateContent.setBottomBar(bottomBarState.value)
    }

    @Composable
    override fun InitializeContent(state: S) {
        templateContent.coroutineScope = rememberCoroutineScope()
        isStarted.value = true
        SideEffect { isStarted.value = true }
        Box(modifier = Modifier.fillMaxSize()) {
            if (isTablet() && tabletDesignEnabled) TabletContent(state)
            else templateContent.Content(state)
        }
    }

    @Composable
    override fun InitializeEvents(events: VROEventLauncher<E>) {
        templateContent.events = events
    }

    @Composable
    override fun InitializeBars() {
        val topBarState = LocalTopBarState.current
        val bottomBarState = LocalBottomBarState.current
        LaunchedEffect(isStarted.value) {
            configureScaffold(topBarState, bottomBarState)
        }
    }
}
