package com.vro.compose.extensions

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableViewModel
import com.vro.compose.initializers.*
import com.vro.compose.screen.*
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROTopBarBaseState
import com.vro.constants.INT_ZERO
import com.vro.core_android.navigation.VROFragmentNavigator
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> NavGraphBuilder.vroComposableScreen(
    viewModel: @Composable () -> VM,
    navigator: VROComposableNavigator<D>,
    content: VROScreen<S, E>,
    enterTransition: EnterTransition? = null,
    exitTransition: ExitTransition? = null,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
) {
    val route = content.destinationRoute()
    composable(
        route = route,
        enterTransition = { enterTransition ?: fadeIn(animationSpec = tween(INT_ZERO))},
        exitTransition = { exitTransition ?: fadeOut(animationSpec = tween(INT_ZERO)) }
    ) {
        VroComposableScreenContent(
            viewModel = viewModel(),
            topBarState = topBarState,
            bottomBarState = bottomBarState,
            navigator = navigator,
            content = content
        )
    }
}

@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VROComposableScreen(
    viewModel: VM,
    navigator: VROComposableNavigator<D>,
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
) {
    VroComposableScreenContent(
        viewModel = viewModel,
        topBarState = topBarState,
        bottomBarState = bottomBarState,
        navigator = navigator,
        content = content
    )
}

@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VROComposableFragmentScreen(
    viewModel: VM,
    navigator: VROFragmentNavigator<D>,
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
) {
    VroComposableScreenContent(
        viewModel = viewModel,
        topBarState = topBarState,
        bottomBarState = bottomBarState,
        navigator = navigator,
        content = content
    )
}

@Composable
internal fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VroComposableScreenContent(
    viewModel: VM,
    navigator: VROComposableNavigator<D>,
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
) {
    content.context = LocalContext.current
    content.events = viewModel
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
        bottomBarState = bottomBarState
    )
    InitializeEventsListener(
        viewModel = viewModel
    )
}


@Composable
internal fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VroComposableScreenContent(
    viewModel: VM,
    navigator: VROFragmentNavigator<D>,
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarBaseState>,
    bottomBarState: MutableState<VROBottomBarBaseState>,
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
        bottomBarState = bottomBarState
    )
    InitializeEventsListener(
        viewModel = viewModel
    )
}
