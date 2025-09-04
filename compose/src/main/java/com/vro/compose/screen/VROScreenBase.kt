package com.vro.compose.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.vro.compose.handler.*
import com.vro.compose.handler.default.*
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
) : KoinScopeComponent {

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
     * Event launcher for handling screen events.
     * Automatically initialized by the screen system.
     */
    lateinit var events: VROEventLauncher<E>

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
     * Main screen container that handles responsive layout.
     * Automatically switches between tablet and mobile layouts based on [tabletDesignEnabled].
     *
     * @param state The current screen state
     * @param topBarState Mutable state for top bar configuration
     * @param bottomBarState Mutable state for bottom bar configuration
     * @param snackbarState Mutable state for snackbar presentation
     */
    @Composable
    internal fun ComposableScreenContainer(
        state: S,
        topBarState: MutableState<VROTopBarBaseState>,
        bottomBarState: MutableState<VROBottomBarBaseState>,
        snackbarState: MutableState<VROSnackBarState>,
    ) {
        InitializeState(state)
        InitializeHandlers()
        InitializeContent(
            state = state,
            topBarState = topBarState,
            bottomBarState = bottomBarState,
            snackbarState = snackbarState
        )
    }

    @Composable
    fun InitializeState(state: S) {
        screenState = state
    }

    @Composable
    fun InitializeHandlers() {
        val context: Context = LocalContext.current
        this.dialogHandler.events = events
        this.dialogHandler.context = context
        this.errorHandler.events = events
        this.errorHandler.context = context
        this.oneTimeHandler.events = events
        this.oneTimeHandler.context = context
    }

    @Composable
    abstract fun InitializeContent(
        state: S,
        topBarState: MutableState<VROTopBarBaseState>,
        bottomBarState: MutableState<VROBottomBarBaseState>,
        snackbarState: MutableState<VROSnackBarState>,
    )

    /**
     * Composable function for tablet-optimized content.
     * Override to provide tablet-specific layouts when [tabletDesignEnabled] is true.
     *
     * @param state The current screen state
     */
    @Composable
    open fun TabletContent(state: S) = Unit
}
