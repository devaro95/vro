package com.vro.compose.dialog

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableViewModel
import com.vro.compose.extensions.getNavParamState
import com.vro.compose.lifecycleevent.createLifecycleEventObserver
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper.VROStateStep

@Suppress("UNCHECKED_CAST")
@Composable
fun <VM : VROComposableViewModel<S, D>, S : VROState, D : VRODestination, E : VROEvent> vroComposableBottomSheetContent(
    viewModel: VM,
    navController: NavController,
    navigator: VROComposableNavigator<D>,
    content: VROComposableBottomSheetContent<S, D, E>,
    showSkeleton: Boolean,
) {
    content.viewModel = viewModel
    content.eventLauncher = viewModel as E
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
                viewModel.startViewModel()
            },
            onResume = { viewModel.onResume() },
            onPause = { viewModel.onPause() }
        )
        screenLifecycle.addObserver(observer)
        onDispose {
            screenLifecycle.removeObserver(observer)
        }
    }
    val stepper = viewModel.stepper.collectAsState(VROStateStep(viewModel.initialState)).value
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
    else if (stepper is VROStateStep) content.ComposableContent(stepper.state)
}