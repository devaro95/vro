/**
 * Package containing initialization functions for screen components.
 */
package com.vro.compose.initializers

import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vro.compose.screen.VROScreen
import com.vro.compose.skeleton.VROSkeletonDefault
import com.vro.compose.states.*
import com.vro.core_android.lifecycleevent.createLifecycleEventObserver
import com.vro.core_android.viewmodel.VROViewModel
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.getStarterParam
import com.vro.state.*
import kotlinx.coroutines.flow.collectLatest

/**
 * Initializes a listener for one-time events from the ViewModel.
 * Handles single-occurrence events and delegates them to the screen.
 *
 * @param VM The ViewModel type that extends [VROViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance to observe
 * @param content The screen content to handle events
 *
 * @see VROOneTimeState For event state definitions
 * @see VROScreen.oneTimeHandler For event handling implementation
 */
@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeOneTimeListener(
    viewModel: VM,
    content: VROScreen<S, E>,
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
 * Initializes the stepper listener for state changes and UI updates.
 * Manages the screen's state flow including:
 * - Skeleton loading states
 * - Regular content states
 * - Dialog presentations
 * - Error handling
 *
 * @param VM The ViewModel type that extends [VROViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance to observe
 * @param content The screen content to render
 * @param screenLifecycle The lifecycle owner for state collection
 * @param topBarState Mutable state for top bar configuration
 * @param bottomBarState Mutable state for bottom bar configuration
 * @param snackbarState Mutable state for snackbar presentation
 *
 * @see VROStepper For state step definitions
 * @see VROScreen.ComposableScreenSkeleton For skeleton implementation
 * @see VROScreen.ComposableScreenContainer For content container
 */
@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeStepperListener(
    viewModel: VM,
    content: VROScreen<S, E>,
    screenLifecycle: Lifecycle,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
    snackbarState: MutableState<VROSnackBarState>,
) {
    val stepper = viewModel.stepper.collectAsStateWithLifecycle(
        initialValue =
            if (content.skeleton::class != VROSkeletonDefault::class) {
                VROStepper.VROSkeletonStep(viewModel.initialState)
            } else {
                VROStepper.VROStateStep(viewModel.initialState)
            },
        lifecycle = screenLifecycle
    ).value

    if (stepper is VROStepper.VROSkeletonStep) {
        content.ComposableScreenSkeleton()
    } else {
        content.ComposableScreenContainer(
            state = stepper.state,
            topBarState = topBarState,
            bottomBarState = bottomBarState,
            snackbarState = snackbarState
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
 * @param topBarState Mutable state for top bar configuration
 * @param bottomBarState Mutable state for bottom bar configuration
 * @param navController The navigation controller for parameter handling
 *
 * @see createLifecycleEventObserver For base observer implementation
 * @see VROScreen.configureScaffold For scaffold setup
 */
@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeLifecycleObserver(
    viewModel: VM,
    content: VROScreen<S, E>,
    screenLifecycle: Lifecycle,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
    navController: NavController,
) {
    DisposableEffect(screenLifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = {
                content.configureScaffold(topBarState, bottomBarState)
                viewModel.onStarter(getStarterParam(navController.currentDestination?.id.toString()))
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