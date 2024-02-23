package com.vro.compose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.vro.compose.VROComposableNavigator.Companion.NAVIGATION_BACK_STATE
import com.vro.compose.extensions.getNavParamState
import com.vro.compose.lifecycleevent.createLifecycleEventObserver
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper.VRODialogStep
import com.vro.state.VROStepper.VROScreenStep
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
@Composable
fun <VM : VROComposableViewModel<S, D>, S : VROState, D : VRODestination, E : VROEvent> vroComposableScreenContent(
    viewModel: VM,
    navController: NavController,
    navigator: VROComposableNavigator<D>,
    content: VROComposableScreenContent<S, D, E>,
    scaffoldState: MutableState<VROComposableScaffoldState>,
    bottomBar: Boolean,
    showSkeleton: Boolean,
) {
    content.viewModel = viewModel
    content.context = LocalContext.current
    content.eventLauncher = viewModel as E
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
                savedBackState?.getLiveData<Serializable>(NAVIGATION_BACK_STATE)?.value?.let {
                    viewModel.onNavResult(it as VROBackResult)
                    savedBackState.remove<Serializable>(NAVIGATION_BACK_STATE)
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
    val stepper = viewModel.stepper.collectAsState(VROScreenStep(viewModel.initialState)).value
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
    val isLoaded by viewModel.screenLoaded
    if (!isLoaded && showSkeleton) content.ComposableSkeleton()
    else {
        when (stepper) {
            is VROScreenStep -> content.ComposableContent(stepper.state)
            is VRODialogStep -> {
                content.ComposableContent(stepper.state)
                content.OnDialog(stepper.dialogState)
            }
        }
    }
}