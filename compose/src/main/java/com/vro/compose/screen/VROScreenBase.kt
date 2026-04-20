package com.vro.compose.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.vro.compose.handler.*
import com.vro.compose.handler.default.*
import com.vro.compose.navigator.VRONavigationClass
import com.vro.compose.skeleton.VROSkeleton
import com.vro.compose.skeleton.VROSkeletonDefault
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.state.VROState
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

/**
 * Abstract base class for all Compose screens in the application.
 * Provides core functionality for screen management including:
 * - State management
 * - Navigation handling
 * - Dependency injection
 * - Responsive layout support
 * - Template integration
 * - Error and dialog handling
 *
 * @param S The state type that extends [VROState]
 * @param E The event type that extends [VROEvent]
 */
abstract class VROScreenBase<S : VROState, E : VROEvent>(
    open val dialogHandler: VRODialogHandler<E> = VRODialogHandlerDefault(),
    open val errorHandler: VROErrorHandler<E> = VROErrorHandlerDefault(),
    open val oneTimeHandler: VROOneTimeHandler<S, E> = VROOneTimeHandlerDefault(),
    open val skeleton: VROSkeleton = VROSkeletonDefault(),
) : VRONavigationClass, KoinScopeComponent {

    /**
     * Koin scope tied to this screen instance.
     * Lazily retrieves an existing scope by the screen's class name, or creates a new one if not found.
     */
    override val scope: Scope by lazy { createScope(this) }

    /**
     * Navigation controller for this screen.
     * Must be initialized before navigation operations.
     */
    lateinit var navController: NavController

    /**
     * Flag indicating whether tablet-specific layouts should be used.
     * Override to enable tablet-optimized designs.
     */
    open val tabletDesignEnabled: Boolean = false

    /**
     * Current screen state managed internally.
     */
    internal lateinit var screenState: S

    /**
     * Updated to true when screen isStarted and state is loaded
     */
    internal val isStarted: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Main screen container that handles responsive layout.
     * Automatically switches between tablet and mobile layouts based on [tabletDesignEnabled].
     *
     * @param state The current screen state
     */
    @Composable
    internal fun ComposableScreenContainer(
        state: S,
        events: VROEventLauncher<E>
    ) {
        InitializeState(
            state = state
        )
        InitializeHandlers(
            events = events
        )
        InitializeContent(
            state = state
        )
    }

    @Composable
    fun InitializeState(state: S) {
        screenState = state
    }

    @Composable
    fun InitializeHandlers(
        events: VROEventLauncher<E>
    ) {
        val context: Context = LocalContext.current
        this.dialogHandler.events = events
        this.dialogHandler.context = context
        this.errorHandler.events = events
        this.errorHandler.context = context
        this.oneTimeHandler.events = events
        this.oneTimeHandler.context = context
    }

    @Composable
    abstract fun InitializeContent(state: S)

    @Composable
    abstract fun InitializeEvents(events: VROEventLauncher<E>)

    @Composable
    abstract fun InitializeBars()


    /**
     * Composable function for tablet-optimized content.
     * Override to provide tablet-specific layouts when [tabletDesignEnabled] is true.
     *
     * @param state The current screen state
     */
    @Composable
    open fun TabletContent(state: S) = Unit
}
