/**
 * Package containing initialization functions for Compose view templates.
 * These functions handle common setup patterns for view models, navigation, and lifecycle.
 */
package com.vro.compose.initializers

import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vro.compose.composition.LocalBottomBarState
import com.vro.compose.composition.LocalTopBarState
import com.vro.compose.screen.VROScreen
import com.vro.compose.skeleton.VROSkeletonDefault
import com.vro.compose.template.*
import com.vro.core_android.lifecycleevent.createLifecycleEventObserver
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.getStarterParam
import com.vro.state.*
import com.vro.viewmodel.VROViewModel
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
 * @param viewModel The ViewModel instance to observe
 * @param content The template content to render
 * @param screenLifecycle The current lifecycle owner
 */
@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> InitializeStepperListener(
    viewModel: VM,
    content: VROTemplate<S, E, M, R>,
    screenLifecycle: Lifecycle
) {
    val stepper = viewModel.stepper.collectAsStateWithLifecycle(
        initialValue = if (content.skeleton::class != VROSkeletonDefault::class) {
            VROStepper.VROSkeletonStep(viewModel.initialState)
        } else {
            VROStepper.VROStateStep(viewModel.initialState)
        },
        lifecycle = screenLifecycle
    ).value

    if (stepper is VROStepper.VROSkeletonStep) {
        content.skeleton.SkeletonContent()
    } else {
        content.ComposableScreenContainer(
            state = stepper.state
        )
        (stepper as? VROStepper.VRODialogStep)?.let {
            content.dialogHandler.OnDialog(it.dialogState)
        }
        (stepper as? VROStepper.VROErrorStep)?.let {
            content.errorHandler.OnError(it.error, it.data)
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
 * @param viewModel The ViewModel instance to observe
 * @param content The template content to handle events
 */
@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> InitializeOneTimeListener(
    viewModel: VM,
    content: VROTemplate<S, E, M, R>,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getOneTimeEvents().collectLatest { oneTime ->
            if (oneTime is VROOneTimeState.Launch) {
                content.oneTimeHandler.onOneTime(oneTime.id, oneTime.state)
                viewModel.clearOneTime()
            }
        }
    }
}

/**
 * Initializes lifecycle observers for the screen.
 * Handles core lifecycle events and coordinates:
 * - Scaffold configuration
 * - Starter parameter processing
 * - Navigation result handling
 * - ViewModel lifecycle methods
 *
 * @param VM The ViewModel type that extends [VROViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance to observe
 * @param content The screen content to configure
 * @param screenLifecycle The lifecycle owner to observe
 * @param navController The navigation controller for parameter handling
 *
 * @see createLifecycleEventObserver For base observer implementation
 * @see VROScreen.configureScaffold For scaffold setup
 */
@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> InitializeLifecycleObserver(
    viewModel: VM,
    content: VROTemplate<S, E, M, R>,
    screenLifecycle: Lifecycle,
    navController: NavController,
) {
    val topBarState = LocalTopBarState.current
    val bottomBarState = LocalBottomBarState.current
    DisposableEffect(screenLifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = {
                content.configureScaffold(topBarState, bottomBarState)
                navController.currentDestination?.id?.let { destinationId ->
                    viewModel.onStarter(getStarterParam(destinationId.toString()))
                }
            },
            onStart = {
                viewModel.onStart()
            },
            onResume = {
                viewModel.getResult()
                viewModel.onResume()
            },
            onPause = { viewModel.onPause() }
        )
        screenLifecycle.addObserver(observer)
        onDispose {
            viewModel.onPause()
            screenLifecycle.removeObserver(observer)
        }
    }
}