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
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

/**
 * Abstract base class for creating reusable Jetpack Compose screens using a state-event-navigation architecture.
 *
 * This class serves as a template for defining screens that handle UI rendering, state management, event dispatching,
 * and navigation logic. It provides a structured way to manage lifecycle observers, skeleton loading UIs, and one-time events.
 *
 * @param S The type of the UI state, extending [VROState].
 * @param E The type of events, extending [VROEvent].
 * @param M The type of state mapper used for transforming state or data.
 * @param R The type of UI renderer, responsible for rendering the screen with the given state and events.
 */
abstract class VROTemplate<
        S : VROState,
        E : VROEvent,
        M : VROTemplateMapper,
        R : VROTemplateRender<E, S>,
        >() : VROScreenBase<S, E>(), KoinScopeComponent {

    abstract val templateContent: VROTemplateContent<S, E, M, R>

    /**
     * Koin scope tied to this screen instance.
     * Lazily retrieves an existing scope by the template's class name, or creates a new one if not found.
     */
    override val scope: Scope by lazy { createScope(this) }

    /**
     * Initializes the scaffold bars (top and bottom) by applying the configuration
     * returned from [templateContent]'s `setTopBar` and `setBottomBar` methods.
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
        topBarState.value = templateContent.setTopBar(topBarState.value)
        bottomBarState.value = templateContent.setBottomBar(bottomBarState.value)
    }

    @Composable
    override fun InitializeContent(state: S) {
        templateContent.coroutineScope = rememberCoroutineScope()
        isStarted.value = true
        SideEffect { isStarted.value = true }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isTablet() && tabletDesignEnabled) {
                TabletContent(state)
            } else {
                templateContent.Content(state)
            }
        }
    }

    @Composable
    override fun InitializeEvents(events: VROEventLauncher<E>) {
        this.templateContent.events = events
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