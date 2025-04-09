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
import com.vro.compose.VROComposableNavigator
import com.vro.compose.initializers.*
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.VROSkeleton
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogData
import com.vro.state.VROState
import org.koin.core.component.KoinScopeComponent
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named

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
        > : KoinScopeComponent {

    /** Koin scope tied to this template class. */
    override val scope = get().getScopeOrNull(this::class.toString()) ?: get().createScope(this::class.toString(), named(this::class.toString()))

    /** Event launcher used to dispatch [VROEvent] instances. */
    lateinit var events: VROEventLauncher<E>

    /** The ViewModel associated with this screen. */
    abstract val viewModel: VM

    /** Reference to the current [VROComposableActivity]. */
    lateinit var activity: VROComposableActivity

    /** Navigation controller used for routing between destinations. */
    lateinit var navController: NavController

    /** Returns a navigator to manage navigation logic. */
    abstract fun createNavigator(): VROComposableNavigator<D>

    /** Optional skeleton view shown while loading. */
    open val skeleton: VROSkeleton? = null

    /** Returns the mapper used to transform or map data/state. */
    abstract fun mapper(): M

    /**
     * Defines how the screen is rendered based on the current state.
     *
     * @param state The current UI state.
     */
    @Composable
    abstract fun render(state: S): R

    /**
     * Composable container that initializes the screen with lifecycle and event listeners.
     *
     * @param navController The [NavController] used for navigation.
     */
    @Composable
    internal fun ComposableTemplateContainer(navController: NavController) {
        this.activity = LocalActivity.current as VROComposableActivity
        this.events = viewModel
        this.navController = navController
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        InitializeLifecycleObserver(
            viewModel = viewModel,
            lifecycle = lifecycle
        )
        InitializeNavigatorListener(
            viewModel = viewModel,
            navigator = createNavigator()
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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            skeleton?.SkeletonContent()
        }
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