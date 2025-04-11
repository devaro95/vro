package com.vro.compose.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.VROSkeleton
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState
import com.vro.compose.template.VROTemplate
import com.vro.compose.utils.isTablet
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.navigation.VROBackResult
import com.vro.state.VRODialogData
import com.vro.state.VROState
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinScopeComponent
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named
import kotlin.reflect.KClass

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
abstract class VROScreenBase<S : VROState, E : VROEvent> : KoinScopeComponent {

    /**
     * Koin scope tied to this screen instance.
     * Lazily retrieves an existing scope by the screen's class name, or creates a new one if not found.
     */
    override val scope = get().getScopeOrNull(this::class.toString()) ?: get().createScope(this::class.toString(), named(this::class.toString()))

    /**
     * Navigation controller for this screen.
     * Must be initialized before navigation operations.
     */
    lateinit var navController: NavController

    /**
     * Optional skeleton loading animation implementation.
     * Set to provide custom loading placeholders.
     */
    open val skeleton: VROSkeleton? = null

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
     * Mutable state for top app bar configuration.
     */
    internal lateinit var topBarState: MutableState<VROTopBarBaseState>

    /**
     * Mutable state for bottom app bar configuration.
     */
    internal lateinit var bottomBarState: MutableState<VROBottomBarBaseState>

    /**
     * Mutable state for snackbar presentation.
     */
    lateinit var snackbarState: MutableState<VROSnackBarState>

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

    /**
     * Coroutine scope tied to the screen's lifecycle.
     */
    lateinit var coroutineScope: CoroutineScope

    /**
     * Composable function that renders the skeleton loading state.
     * Uses the provided [skeleton] implementation if available.
     */
    @Composable
    internal fun ComposableScreenSkeleton() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            skeleton?.SkeletonContent()
        }
    }

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
        this.topBarState = topBarState
        this.bottomBarState = bottomBarState
        this.snackbarState = snackbarState
        screenState = state
        coroutineScope = rememberCoroutineScope()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isTablet() && tabletDesignEnabled) {
                ScreenTabletContent(state)
            } else {
                ScreenContent(state)
            }
        }
    }

    /**
     * Adds a template component to the screen.
     *
     * @param templateClass The KClass of the template to add
     */
    @Composable
    fun AddTemplate(templateClass: KClass<out VROTemplate<*, *, *, *, *, *>>) {
        val template: VROTemplate<*, *, *, *, *, *> = scope.get(templateClass)
        template.ComposableTemplateContainer(navController)
    }

    /**
     * Abstract composable function that must be implemented to provide the main screen content.
     *
     * @param state The current screen state
     */
    @Composable
    abstract fun ScreenContent(state: S)

    /**
     * Composable function for tablet-optimized content.
     * Override to provide tablet-specific layouts when [tabletDesignEnabled] is true.
     *
     * @param state The current screen state
     */
    @Composable
    open fun ScreenTabletContent(state: S) = Unit

    /**
     * Abstract composable function that must be implemented to provide a preview of the screen.
     * Annotated with [VROLightMultiDevicePreview] for multi-device preview support.
     */
    @VROLightMultiDevicePreview
    @Composable
    abstract fun ScreenPreview()

    /**
     * Handles dialog presentation.
     * Override to implement custom dialog handling.
     *
     * @param data The dialog configuration data
     */
    @SuppressLint("ComposableNaming")
    @Composable
    open fun onDialog(data: VRODialogData) = Unit

    /**
     * Handles error presentation.
     * Override to implement custom error handling.
     *
     * @param error The error that occurred
     * @param data Optional additional error data
     */
    @SuppressLint("ComposableNaming")
    @Composable
    open fun onError(error: Throwable, data: Any?) = Unit

    /**
     * Handles one-time events.
     * Override to implement custom one-time event handling.
     *
     * @param id The event ID
     * @param state The current screen state
     */
    open fun oneTimeHandler(id: Int, state: S) = Unit

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
}