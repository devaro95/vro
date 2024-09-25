package com.vro.compose.extensions

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableViewModel
import com.vro.compose.initializers.*
import com.vro.compose.screen.*
import com.vro.compose.states.VROBottomBarState
import com.vro.compose.states.VROTopBarState
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
    topBarState: MutableState<VROTopBarState?>,
    bottomBarState: MutableState<VROBottomBarState?>,
) {
    composable(
        content.destinationRoute(),
        enterTransition = { enterTransition },
        exitTransition = { exitTransition ?: fadeOut(animationSpec = tween(INT_ZERO)) }
    ) {
        VroComposableScreenContent(
            viewModel = viewModel.invoke(),
            navController = navigator.navController,
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
    topBarState: MutableState<VROTopBarState?>,
    bottomBarState: MutableState<VROBottomBarState?>,
) {
    VroComposableScreenContent(
        viewModel = viewModel,
        navController = navigator.navController,
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
    topBarState: MutableState<VROTopBarState?>,
    bottomBarState: MutableState<VROBottomBarState?>,
) {
    VroComposableScreenContent(
        viewModel = viewModel,
        navController = navigator.navController,
        topBarState = topBarState,
        bottomBarState = bottomBarState,
        navigator = navigator,
        content = content
    )
}

@Composable
internal fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VroComposableScreenContent(
    viewModel: VM,
    navController: NavController,
    navigator: VROComposableNavigator<D>,
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarState?>,
    bottomBarState: MutableState<VROBottomBarState?>,
) {
    content.context = LocalContext.current
    content.eventListener = viewModel
    BackHandler(true) { viewModel.onBackSystem() }
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    InitializeLifecycleObserver(
        viewModel = viewModel,
        content = content,
        screenLifecycle = screenLifecycle,
        topBarState = topBarState,
        bottomBarState = bottomBarState,
        navController = navController,
    )
    InitializeBarsListeners(
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
        screenLifecycle = screenLifecycle
    )
}

@Composable
internal fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VroComposableScreenContent(
    viewModel: VM,
    navController: NavController,
    navigator: VROFragmentNavigator<D>,
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarState?>,
    bottomBarState: MutableState<VROBottomBarState?>,
) {
    content.context = LocalContext.current
    content.eventListener = viewModel
    BackHandler(true) { viewModel.onBackSystem() }
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    InitializeLifecycleObserver(
        viewModel = viewModel,
        screenLifecycle = screenLifecycle,
        navController = navController,
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
        screenLifecycle = screenLifecycle
    )
}
