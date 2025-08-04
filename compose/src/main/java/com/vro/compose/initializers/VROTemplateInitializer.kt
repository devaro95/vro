/**
 * Package containing initialization functions for Compose view templates.
 * These functions handle common setup patterns for view models, navigation, and lifecycle.
 */
package com.vro.compose.initializers

import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vro.compose.VROComposableActivity
import com.vro.compose.navigator.VROTemplateNavigator
import com.vro.compose.template.*
import com.vro.core_android.lifecycleevent.createLifecycleEventObserver
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.*
import kotlinx.coroutines.flow.collectLatest

/**
 * Initializes and manages the stepper listener for state changes.
 * Handles skeleton loading states, regular content states, dialogs, and errors.
 *
 * @param VM The ViewModel type that extends [VROTemplateViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 * @param M The mapper type that extends [VROTemplateMapper]
 * @param R The render type that extends [VROTemplateRender]
 *
 * @param viewModel The ViewModel instance to observe
 * @param content The template content to render
 * @param lifecycle The current lifecycle owner
 */
@Composable
fun <VM : VROTemplateViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> InitializeStepperListener(
    viewModel: VM,
    content: VROTemplate<VM, S, D, E, M, R>,
    lifecycle: Lifecycle,
) {
    val stepper = viewModel.stepper.collectAsStateWithLifecycle(
        initialValue =
            content.skeleton?.let {
                VROStepper.VROSkeletonStep(viewModel.initialState)
            } ?: VROStepper.VROStateStep(viewModel.initialState),
        lifecycle = lifecycle
    ).value
    if (stepper is VROStepper.VROSkeletonStep) {
        content.TemplateScreenSkeleton()
    } else {
        content.TemplateContent(stepper.state)
        (stepper as? VROStepper.VRODialogStep)?.let {
            content.onDialog(it.dialogState)
        }
        (stepper as? VROStepper.VROErrorStep)?.let {
            content.onError(it.error, it.data)
        }
    }
}

/**
 * Initializes a one-time event listener for handling single-occurrence events.
 *
 * @param VM The ViewModel type that extends [VROTemplateViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 * @param M The mapper type that extends [VROTemplateMapper]
 * @param R The render type that extends [VROTemplateRender]
 *
 * @param viewModel The ViewModel instance to observe
 * @param content The template content to handle events
 */
@Composable
fun <VM : VROTemplateViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> InitializeOneTimeListener(
    viewModel: VM,
    content: VROTemplate<VM, S, D, E, M, R>,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getOneTimeEvents().collectLatest { oneTime ->
            if (oneTime is VROOneTimeState.Launch) {
                content.oneTimeHandler(oneTime.id, oneTime.state)
                viewModel.clearOneTime()
            }
        }
    }
}

/**
 * Initializes lifecycle observers for the ViewModel.
 * Handles onStart, onResume, and onPause lifecycle events.
 *
 * @param VM The ViewModel type that extends [VROTemplateViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance to observe
 * @param lifecycle The current lifecycle owner
 */
@Composable
fun <VM : VROTemplateViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeLifecycleObserver(
    viewModel: VM,
    lifecycle: Lifecycle,
) {
    DisposableEffect(lifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = {},
            onStart = { viewModel.onStart() },
            onResume = { viewModel.onResume() },
            onPause = { viewModel.onPause() }
        )
        lifecycle.addObserver(observer)
        onDispose {
            viewModel.onDestroy()
            lifecycle.removeObserver(observer)
        }
    }
}

/**
 * Initializes the navigation system for a template-based screen using a [VROTemplateNavigator].
 *
 * Typically used inside a `@Composable` function during screen setup.
 *
 * @param VM The ViewModel type, extending [VROTemplateViewModel], which emits navigation events.
 * @param S The state type, extending [VROState], managed by the ViewModel.
 * @param D The destination type, extending [VRODestination], representing navigation targets.
 * @param E The event type, extending [VROEvent], representing UI events.
 * @param M The mapper type, used to map state/data (unused in this function, but part of the template).
 * @param R The renderer type, responsible for rendering UI with state and events.
 *
 * @param viewModel The ViewModel instance from which to observe navigation state.
 * @param activity The [VROComposableActivity] hosting the screen, needed for context-bound navigation.
 * @param navController The Jetpack Compose [NavController] handling navigation actions.
 * @param content The screen template implementing [VROTemplate], containing the navigator.
 */
@Composable
fun <VM : VROTemplateViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> InitializeNavigatorListener(
    viewModel: VM,
    activity: VROComposableActivity,
    navController: NavController,
    content: VROTemplate<VM, S, D, E, M, R>,
) {
    content.navigator.initialize(
        activity = activity,
        navController = navController
    )
    LaunchedEffect(key1 = Unit) {
        viewModel.getNavigationState().collect {
            it?.destination?.let { destination ->
                if (!destination.isNavigated) {
                    content.navigator.navigate(destination)
                    destination.setNavigated()
                }
            } ?: content.navigator.navigateBack(it?.backResult)
        }
    }
}