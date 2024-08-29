package com.vro.compose.screen

import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableViewModel
import com.vro.compose.extensions.getNavParamState
import com.vro.compose.lifecycleevent.createLifecycleEventObserver
import com.vro.compose.states.VROBottomBarState
import com.vro.compose.states.VROTopBarState
import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.state.*
import kotlinx.coroutines.flow.collectLatest
import java.io.Serializable

@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeNavigatorListener(
    viewModel: VM,
    navigator: VROComposableNavigator<D>,
) {
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
}

@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeOneTimeListener(
    viewModel: VM,
    content: VROScreen<S, E>,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.oneTime.collectLatest { oneTime ->
            if (oneTime is VROOneTimeState.Launch) {
                content.oneTimeHandler(oneTime.id, oneTime.state)
                viewModel.clearOneTime()
            }
        }
    }
}

@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeStepperListener(
    viewModel: VM,
    content: VROScreen<S, E>,
    screenLifecycle: Lifecycle,
) {
    val stepper =
        viewModel.stepper.collectAsStateWithLifecycle(
            initialValue = VROStepper.VROStateStep(viewModel.initialState),
            lifecycle = screenLifecycle
        ).value
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

@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeLifecycleObserver(
    viewModel: VM,
    content: VROScreen<S, E>,
    screenLifecycle: Lifecycle,
    topBarState: MutableState<VROTopBarState?>,
    bottomBarState: MutableState<VROBottomBarState?>,
    navController: NavController,
) {
    DisposableEffect(screenLifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = {
                content.configureScaffold(topBarState, bottomBarState)
                viewModel.onCreate(getNavParamState(navController.currentDestination?.route.toString()))
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
}