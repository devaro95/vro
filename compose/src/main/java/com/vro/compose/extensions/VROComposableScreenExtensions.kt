package com.vro.compose.extensions

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableScreenContent
import com.vro.compose.VROComposableViewModel
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.compose.VroComposableScreenContent
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

fun <VM : VROComposableViewModel<S, D>, S : VROState, D : VRODestination, E : VROEvent> NavGraphBuilder.vroComposableScreen(
    viewModel: @Composable () -> VM,
    navigator: VROComposableNavigator<D>,
    content: VROComposableScreenContent<S, D, E>,
    enterTransition: EnterTransition? = null,
    exitTransition: ExitTransition? = null,
    scaffoldState: MutableState<VROComposableScaffoldState>,
    bottomBar: Boolean = false,
    showSkeleton: Boolean = false,
) {
    composable(
        content.destinationRoute(),
        enterTransition = { enterTransition },
        exitTransition = { exitTransition ?: fadeOut(animationSpec = tween(50)) }
    ) {
        VroComposableScreenContent(
            viewModel = viewModel.invoke(),
            navController = navigator.navController,
            scaffoldState = scaffoldState,
            navigator = navigator,
            bottomBar = bottomBar,
            content = content,
            showSkeleton = showSkeleton
        )
    }
}

@Composable
fun <VM : VROComposableViewModel<S, D>, S : VROState, D : VRODestination, E : VROEvent> VROComposableScreen(
    viewModel: VM,
    navigator: VROComposableNavigator<D>,
    content: VROComposableScreenContent<S, D, E>,
    scaffoldState: MutableState<VROComposableScaffoldState>,
    bottomBar: Boolean = false,
    showSkeleton: Boolean,
) {
    VroComposableScreenContent(
        viewModel = viewModel,
        navController = navigator.navController,
        scaffoldState = scaffoldState,
        navigator = navigator,
        bottomBar = bottomBar,
        content = content,
        showSkeleton = showSkeleton
    )
}

