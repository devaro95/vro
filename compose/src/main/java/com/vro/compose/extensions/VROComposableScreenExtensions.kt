package com.vro.compose.extensions

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableViewModel
import com.vro.compose.lifecycleevent.createLifecycleEventObserver
import com.vro.compose.screen.VROScreen
import com.vro.compose.states.VROBottomBarState
import com.vro.compose.states.VROTopBarState
import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper
import java.io.Serializable

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
        exitTransition = { exitTransition ?: fadeOut(animationSpec = tween(0)) }
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
internal fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VroComposableScreenContent(
    viewModel: VM,
    navController: NavController,
    navigator: VROComposableNavigator<D>,
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarState?>,
    bottomBarState: MutableState<VROBottomBarState?>,
) {
    content.context = LocalContext.current
    BackHandler(true) { viewModel.onBackSystem() }
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(screenLifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = {
                content.configureScaffold(topBarState, bottomBarState)
                viewModel.onNavParam(getNavParamState(navController.currentDestination?.route.toString()))
            },
            onStart = { viewModel.startViewModel() },
            onResume = {
                val savedBackState = navController.currentBackStackEntry?.savedStateHandle
                savedBackState?.getLiveData<Serializable>(VROComposableNavigator.NAVIGATION_BACK_STATE)?.value?.let {
                    viewModel.onNavResult(it as VROBackResult)
                    savedBackState.remove<Serializable>(VROComposableNavigator.NAVIGATION_BACK_STATE)
                }
                viewModel.onResume()
            },
            onPause = { viewModel.onPause() }
        )
        screenLifecycle.addObserver(observer)
        onDispose {
            screenLifecycle.removeObserver(observer)
        }
    }
    val stepper =
        viewModel.stepper.collectAsState(VROStepper.VROStateStep(viewModel.initialState)).value
    LaunchedEffect(key1 = Unit) {
        viewModel.navigationState.collect {
            it?.destination?.let { destination ->
                if (!destination.isNavigated) {
                    navigator.navigate(destination)
                    destination.setNavigated()
                }
            } ?: navigator.navigateBack(it?.backResult)
        }
    }
    content.eventListener = viewModel
    when (stepper) {
        is VROStepper.VROStateStep -> content.ComposableScreenContainer(stepper.state)
        is VROStepper.VROSkeletonStep -> content.ComposableScreenSkeleton()
        is VROStepper.VROErrorStep -> content.OnError(stepper.error, stepper.data)
        is VROStepper.VRODialogStep -> {
            content.ComposableScreenContainer(stepper.state)
            content.OnDialog(stepper.dialogState)
        }
    }
}
