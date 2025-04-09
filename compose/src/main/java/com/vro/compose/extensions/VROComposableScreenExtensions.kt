/**
 * Package containing extension functions for Compose screen navigation and composition.
 */
package com.vro.compose.extensions

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vro.compose.VROComposableActivity
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableViewModel
import com.vro.compose.initializers.*
import com.vro.compose.screen.*
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROSnackBarState
import com.vro.compose.states.VROTopBarBaseState
import com.vro.constants.INT_ZERO
import com.vro.core_android.navigation.VROFragmentNavigator
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

/**
 * Adds a Compose screen to the navigation graph with optional transitions.
 *
 * @param VM The ViewModel type that extends [VROComposableViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel Factory function for the ViewModel
 * @param navigator The navigation controller
 * @param content The screen content composable
 * @param enterTransition Optional enter transition animation
 * @param exitTransition Optional exit transition animation
 * @param topBarState Mutable state for top bar configuration
 * @param bottomBarState Mutable state for bottom bar configuration
 * @param snackbarState Mutable state for snackbar presentation
 *
 * @see VROComposableScreen For the screen implementation
 * @see VroComposableScreenContent For internal content handling
 */
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> NavGraphBuilder.vroComposableScreen(
    viewModel: @Composable () -> VM,
    navigator: VROComposableNavigator<D>,
    content: VROScreen<S, E>,
    enterTransition: EnterTransition? = null,
    exitTransition: ExitTransition? = null,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
    snackbarState: MutableState<VROSnackBarState>,
) {
    val route = content.destinationRoute()
    composable(
        route = route,
        enterTransition = { enterTransition ?: fadeIn(animationSpec = tween(INT_ZERO)) },
        exitTransition = { exitTransition ?: fadeOut(animationSpec = tween(INT_ZERO)) }
    ) {
        VroComposableScreenContent(
            viewModel = viewModel(),
            topBarState = topBarState,
            bottomBarState = bottomBarState,
            snackbarState = snackbarState,
            navigator = navigator,
            content = content,
        )
    }
}

/**
 * Composable function for a full-screen Compose component.
 * Initializes all necessary screen components and state listeners.
 *
 * @param VM The ViewModel type that extends [VROComposableViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance
 * @param navigator The navigation controller
 * @param content The screen content composable
 * @param topBarState Mutable state for top bar configuration
 * @param bottomBarState Mutable state for bottom bar configuration
 * @param snackbarState Mutable state for snackbar presentation
 *
 * @see VroComposableScreenContent For internal implementation
 */
@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VROComposableScreen(
    viewModel: VM,
    navigator: VROComposableNavigator<D>,
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
    snackbarState: MutableState<VROSnackBarState>,
) {
    VroComposableScreenContent(
        viewModel = viewModel,
        topBarState = topBarState,
        bottomBarState = bottomBarState,
        navigator = navigator,
        snackbarState = snackbarState,
        content = content
    )
}

/**
 * Composable function for a fragment-based Compose screen.
 * Initializes all necessary screen components with fragment navigation support.
 *
 * @param VM The ViewModel type that extends [VROComposableViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance
 * @param navigator The fragment navigation controller
 * @param content The screen content composable
 * @param topBarState Mutable state for top bar configuration
 * @param bottomBarState Mutable state for bottom bar configuration
 * @param snackbarState Mutable state for snackbar presentation
 *
 * @see VroComposableScreenContent For internal implementation
 */
@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VROComposableFragmentScreen(
    viewModel: VM,
    navigator: VROFragmentNavigator<D>,
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
    snackbarState: MutableState<VROSnackBarState>,
) {
    VroComposableScreenContent(
        viewModel = viewModel,
        topBarState = topBarState,
        bottomBarState = bottomBarState,
        navigator = navigator,
        content = content,
        snackbarState = snackbarState
    )
}

/**
 * Internal implementation for Compose screen content.
 * Initializes all screen components and state listeners.
 *
 * @param VM The ViewModel type that extends [VROComposableViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance
 * @param navigator The navigation controller
 * @param content The screen content composable
 * @param topBarState Mutable state for top bar configuration
 * @param bottomBarState Mutable state for bottom bar configuration
 * @param snackbarState Mutable state for snackbar presentation
 *
 * @see InitializeLifecycleObserver For lifecycle handling
 * @see InitializeOneTimeListener For event handling
 * @see InitializeNavigatorListener For navigation handling
 * @see InitializeStepperListener For state management
 */
@Composable
internal fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VroComposableScreenContent(
    viewModel: VM,
    navigator: VROComposableNavigator<D>,
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
    snackbarState: MutableState<VROSnackBarState>,
) {
    content.context = LocalContext.current
    content.events = viewModel
    content.navController = navigator.navController
    BackHandler(true) { viewModel.onBackSystem() }
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    InitializeLifecycleObserver(
        viewModel = viewModel,
        content = content,
        screenLifecycle = screenLifecycle,
        topBarState = topBarState,
        bottomBarState = bottomBarState,
        navController = navigator.navController,
    )
    InitializeOneTimeListener(
        viewModel = viewModel,
        content = content
    )
    InitializeNavigatorListener(
        viewModel = viewModel,
        navigator = navigator
    )
    InitializeStepperListener(
        viewModel = viewModel,
        content = content,
        screenLifecycle = screenLifecycle,
        topBarState = topBarState,
        bottomBarState = bottomBarState,
        snackbarState = snackbarState
    )
    InitializeEventsListener(
        viewModel = viewModel
    )
}

/**
 * Internal implementation for fragment-based Compose screen content.
 * Initializes all screen components with fragment navigation support.
 *
 * @param VM The ViewModel type that extends [VROComposableViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance
 * @param navigator The fragment navigation controller
 * @param content The screen content composable
 * @param topBarState Mutable state for top bar configuration
 * @param bottomBarState Mutable state for bottom bar configuration
 * @param snackbarState Mutable state for snackbar presentation
 *
 * @see InitializeLifecycleObserver For lifecycle handling
 * @see InitializeOneTimeListener For event handling
 * @see InitializeNavigatorListener For navigation handling
 * @see InitializeStepperListener For state management
 */
@Composable
internal fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VroComposableScreenContent(
    viewModel: VM,
    navigator: VROFragmentNavigator<D>,
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
    snackbarState: MutableState<VROSnackBarState>,
) {
    content.context = LocalContext.current
    content.events = viewModel

    BackHandler(true) { viewModel.onBackSystem() }
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    InitializeLifecycleObserver(
        viewModel = viewModel,
        screenLifecycle = screenLifecycle,
        navController = navigator.navController,
        content = content,
        topBarState = topBarState,
        bottomBarState = bottomBarState
    )
    InitializeOneTimeListener(
        viewModel = viewModel,
        content = content
    )
    InitializeNavigatorListener(
        viewModel = viewModel,
        navigator = navigator
    )
    InitializeStepperListener(
        viewModel = viewModel,
        content = content,
        screenLifecycle = screenLifecycle,
        topBarState = topBarState,
        bottomBarState = bottomBarState,
        snackbarState = snackbarState
    )
    InitializeEventsListener(
        viewModel = viewModel
    )
}