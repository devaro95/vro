package com.vro.compose.extensions

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
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

@ExperimentalMaterial3Api
fun <VM : VROComposableViewModel<S, D>, S : VROState, D : VRODestination, E : VROEvent> NavGraphBuilder.vroComposableScreen(
    viewModel: @Composable () -> VM,
    navController: NavController,
    navigator: VROComposableNavigator<D>,
    content: VROComposableScreen<S, D, E>,
    enterTransition: EnterTransition? = null,
    exitTransition: ExitTransition? = null,
    scaffoldState: MutableState<VROComposableScaffoldState>,
    showBottomBar: Boolean = true,
) {
    composable(
        content.destinationRoute(),
        enterTransition = { enterTransition },
        exitTransition = { exitTransition }
    ) {
        scaffoldState.value = scaffoldState.value.copy(showBottomBar = showBottomBar)
        content.CreateScreen(
            viewModel = viewModel.invoke(),
            navController = navController,
            scaffoldState = scaffoldState,
            navigator = navigator
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun <VM : VROComposableViewModel<S, D>, S : VROState, D : VRODestination, E : VROEvent> VROComposableScreen(
    viewModel: VM,
    navController: NavController,
    navigator: VROComposableNavigator<D>,
    content: VROComposableScreen<S, D, E>,
    scaffoldState: MutableState<VROComposableScaffoldState>,
    showBottomBar: Boolean = true,
) {
    scaffoldState.value = scaffoldState.value.copy(showBottomBar = showBottomBar)
    content.CreateScreen(
        viewModel = viewModel,
        navController = navController,
        scaffoldState = scaffoldState,
        navigator = navigator
    )
}