package com.vro.compose.initializers

import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vro.compose.VROComposableViewModel
import com.vro.compose.screen.VROScreen
import com.vro.compose.states.*
import com.vro.core_android.lifecycleevent.createLifecycleEventObserver
import com.vro.core_android.navigation.VRONavigator
import com.vro.event.VROEvent
import com.vro.navigation.*
import com.vro.state.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeNavigatorListener(
    viewModel: VM,
    navigator: VRONavigator<D>,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getNavigationState().collect {
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
        viewModel.getOneTimeEvents().collectLatest { oneTime ->
            if (oneTime is VROOneTimeState.Launch) {
                content.oneTimeHandler(oneTime.id, oneTime.state)
                viewModel.clearOneTime()
            }
        }
    }
}

@Composable
fun <S : VROState, E : VROEvent> InitializeBarsListeners(
    content: VROScreen<S, E>,
    topBarState: MutableState<VROTopBarState?>,
    bottomBarState: MutableState<VROBottomBarState?>,
) {
    LaunchedEffect(key1 = Unit) {
        content.topBarFlow.collectLatest { topBar ->
            if (topBar is VROTopBarLaunchState.Launch) {
                topBarState.value = topBar.state
                content.clearTopBarFlow()
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        content.bottomBarFlow.collectLatest { bottomBar ->
            if (bottomBar is VROBottomBarLaunchState.Launch) {
                bottomBarState.value = bottomBar.state
                content.clearTopBarFlow()
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
    val stepper = viewModel.stepper.collectAsStateWithLifecycle(
        initialValue = VROStepper.VROStateStep(viewModel.initialState),
        lifecycle = screenLifecycle
    ).value

    content.ComposableScreenContainer(stepper.state)
    (stepper as? VROStepper.VRODialogStep)?.let {
        content.onDialog(it.dialogState)
    }
    (stepper as? VROStepper.VROErrorStep)?.let {
        content.onError(it.error, it.data)
    }
    (stepper as? VROStepper.VROSkeletonStep)?.let {
        content.ComposableScreenSkeleton()
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
                viewModel.onCreate(getStarterParam(navController.currentDestination?.id.toString()))
            },
            onStart = { viewModel.onStart() },
            onResume = {
                getResultParam(navController.currentDestination?.id.toString())?.let {
                    viewModel.onNavResult(it)
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