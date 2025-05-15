package com.vro.compose.template

import android.annotation.SuppressLint
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.vro.compose.VROComposableActivity
import com.vro.compose.initializers.*
import com.vro.compose.navigator.VROTemplateNav
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.VROSkeleton
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogData
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
 * @param VM The type of the ViewModel, extending [VROTemplateViewModel].
 * @param S The type of the UI state, extending [VROState].
 * @param D The type of navigation destination, extending [VRODestination].
 * @param E The type of events, extending [VROEvent].
 * @param M The type of state mapper used for transforming state or data.
 * @param R The type of UI renderer, responsible for rendering the screen with the given state and events.
 */
abstract class VROTemplate<
        VM : VROTemplateViewModel<S, D, E>,
        S : VROState,
        D : VRODestination,
        E : VROEvent,
        M : VROTemplateMapper,
        R : VROTemplateRender<E, S>,
        >() : KoinScopeComponent {

    /**
     * Koin scope tied to this screen instance.
     * Lazily retrieves an existing scope by the template's class name, or creates a new one if not found.
     */
    override val scope: Scope by lazy { createScope(this) }

    /**
     * Event launcher used to dispatch [VROEvent] instances.
     * This should be initialized in the screen lifecycle to handle one-time or stateful events.
     */
    lateinit var events: VROEventLauncher<E>

    /**
     * The ViewModel associated with this screen.
     * Responsible for managing UI state and coordinating business logic.
     */
    abstract val viewModel: VM

    /**
     * Optional UI skeleton component.
     * Used to render loading placeholders while real content is being fetched or processed.
     */
    open val skeleton: VROSkeleton? = null

    /**
     * Navigation handler for the screen.
     * Abstracts navigation actions and destination routing.
     */
    abstract val navigator: VROTemplateNav<D>

    /**
     * Returns the mapper responsible for transforming raw data or state into a renderable form.
     * Typically used to prepare content for the UI layer.
     */
    abstract val mapper: M

    /**
     * Defines how the screen is rendered based on the current state.
     *
     * @param state The current UI state.
     */
    @Composable
    abstract fun render(state: S): R

    /**
     * Composable container that initializes the screen with all necessary lifecycle and state listeners.
     * This function serves as the main entry point for setting up the template's core functionality.
     *
     * Initializes the following components in order:
     * 1. Lifecycle observer (handles onStart/onResume/onPause events)
     * 2. Navigation listener (handles navigation events from ViewModel)
     * 3. One-time event listener (handles single-occurrence events)
     * 4. Stepper listener (manages state changes including loading/error/dialog states)
     * 5. Event listener (handles continuous event streams)
     *
     * @param navController The [NavController] used for navigation within this container.
     *                     Will be used to create the appropriate navigator instance.
     *
     * @see InitializeLifecycleObserver For lifecycle event handling implementation
     * @see InitializeNavigatorListener For navigation event handling implementation
     * @see InitializeOneTimeListener For one-time event processing
     * @see InitializeStepperListener For state management and UI rendering
     * @see InitializeEventsListener For continuous event stream processing
     *
     * @throws ClassCastException if the local activity cannot be cast to [VROComposableActivity]
     */
    @Composable
    fun ComposableTemplateContainer(navController: NavController) {
        val activity = LocalActivity.current as VROComposableActivity
        this.events = viewModel
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        InitializeLifecycleObserver(
            viewModel = viewModel,
            lifecycle = lifecycle
        )
        InitializeNavigatorListener(
            viewModel = viewModel,
            activity = activity,
            navController = navController,
            content = this
        )
        InitializeOneTimeListener(
            viewModel = viewModel,
            content = this
        )
        InitializeStepperListener(
            viewModel = viewModel,
            content = this,
            lifecycle = lifecycle
        )
        InitializeEventsListener(
            viewModel = viewModel
        )
    }

    /**
     * Optional skeleton UI displayed while the main content is loading.
     */
    @Composable
    internal fun TemplateScreenSkeleton() {
        skeleton?.SkeletonContent()
    }

    /**
     * Abstract composable that defines the main UI content of the screen.
     *
     * @param state The current UI state.
     */
    @Composable
    abstract fun TemplateContent(state: S)

    /**
     * Composable used to preview the screen on multiple device sizes with light theme.
     */
    @VROLightMultiDevicePreview
    @Composable
    abstract fun TemplatePreview()

    /**
     * Optional composable to display dialogs.
     *
     * @param data The dialog data.
     */
    @SuppressLint("ComposableNaming")
    @Composable
    open fun onDialog(data: VRODialogData) = Unit

    /**
     * Optional composable to display error UI or feedback.
     *
     * @param error The thrown exception.
     * @param data Additional context data.
     */
    @SuppressLint("ComposableNaming")
    @Composable
    open fun onError(error: Throwable, data: Any?) = Unit

    /**
     * Optional one-time event handler.
     *
     * @param id Identifier of the event.
     * @param state The current UI state.
     */
    open fun oneTimeHandler(id: Int, state: S) = Unit

    /**
     * Dispatches an event to the [viewModel] via the [events] launcher.
     *
     * @param event The event to trigger.
     */
    fun event(event: E) {
        events.doEvent(event)
    }
}