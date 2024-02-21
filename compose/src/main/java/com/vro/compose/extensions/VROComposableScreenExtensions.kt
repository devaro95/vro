package com.vro.compose.extensions

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableScreen
import com.vro.compose.VROComposableViewModel
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

fun <VM : VROComposableViewModel<S, D>, S : VROState, D : VRODestination, E : VROEvent> NavGraphBuilder.vroComposableScreen(
    viewModel: @Composable () -> VM,
    navController: NavController,
    navigator: VROComposableNavigator<D>,
    content: VROComposableScreen<S, D, E>,
    enterTransition: EnterTransition? = null,
    exitTransition: ExitTransition? = null,
    scaffoldState: MutableState<VROComposableScaffoldState>,
    bottomBar: Boolean = false,
) {
    composable(
        content.destinationRoute(),
        enterTransition = { enterTransition ?: EnterTransition.None },
        exitTransition = { exitTransition ?: ExitTransition.None }
    ) {
        content.CreateScreen(
            viewModel = viewModel.invoke(),
            navController = navController,
            scaffoldState = scaffoldState,
            navigator = navigator,
            bottomBar = bottomBar
        )
    }
}

@Composable
fun <VM : VROComposableViewModel<S, D>, S : VROState, D : VRODestination, E : VROEvent> VROComposableScreen(
    viewModel: VM,
    navController: NavController,
    navigator: VROComposableNavigator<D>,
    content: VROComposableScreen<S, D, E>,
    scaffoldState: MutableState<VROComposableScaffoldState>,
    bottomBar: Boolean = false,
) {
    content.CreateScreen(
        viewModel = viewModel,
        navController = navController,
        scaffoldState = scaffoldState,
        navigator = navigator,
        bottomBar = bottomBar
    )
}