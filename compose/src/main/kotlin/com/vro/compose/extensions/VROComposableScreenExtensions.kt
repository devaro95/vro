package com.vro.compose.extensions

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vro.compose.VROComposableNavigator
import com.vro.compose.lifecycleevent.createLifecycleEventObserver
import com.vro.compose.screen.VROScreen
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.event.VROEvent
import com.vro.compose.viewmodel.VROComposableViewModel
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper
import kotlinx.coroutines.flow.collectLatest
import java.io.Serializable

fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> NavGraphBuilder.vroComposableScreen(
    viewModel: @Composable () -> VM,
    navigator: VROComposableNavigator<D>,
    content: VROScreen<S, E>,
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
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VROComposableScreen(
    viewModel: VM,
    navigator: VROComposableNavigator<D>,
    content: VROScreen<S, E>,
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

@Composable
internal fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> VroComposableScreenContent(
    viewModel: VM,
    navController: NavController,
    navigator: VROComposableNavigator<D>,
    content: VROScreen<S, E>,
    scaffoldState: MutableState<VROComposableScaffoldState>,
    bottomBar: Boolean,
    showSkeleton: Boolean,
) {
    content.context = LocalContext.current
    BackHandler(true) { navigator.navigateBack(null) }
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(screenLifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = {
                viewModel.isLoaded()
                viewModel.onNavParam(getNavParamState(navController.currentDestination?.route.toString()))
            },
            onStart = {
                content.configureScaffold(scaffoldState, bottomBar)
                viewModel.startViewModel()
            },
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
    val stepper = viewModel.stepper.collectAsState(VROStepper.VROStateStep(viewModel.initialState)).value
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
    val isLoaded by viewModel.screenLoaded.collectAsState(initial = false)
    if (!isLoaded && showSkeleton) content.ComposableSkeleton()
    else {
        when (stepper) {
            is VROStepper.VROStateStep -> content.ComposableSectionContainer(stepper.state, viewModel)
            is VROStepper.VRODialogStep -> {
                content.ComposableSectionContainer(stepper.state, viewModel)
                content.OnDialog(stepper.dialogState)
            }
        }
    }
}
