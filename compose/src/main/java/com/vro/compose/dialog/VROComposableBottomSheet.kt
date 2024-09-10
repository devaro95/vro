package com.vro.compose.dialog

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.vro.compose.VROComposableViewModel
import com.vro.compose.lifecycleevent.createLifecycleEventObserver
import com.vro.compose.screen.InitializeLifecycleObserver
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper.VROSkeletonStep
import com.vro.state.VROStepper.VROStateStep

@Composable
internal fun <VM : VROComposableViewModel<S, D, E>, S : VROState, E : VROEvent, D : VRODestination> VroComposableNavBottomSheetContent(
    viewModel: VM,
    content: VROComposableBottomSheetContent<S, E>,
    navController: NavController,
    onDismiss: () -> Unit,
) {
    content.context = LocalContext.current
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    InitializeLifecycleObserver(
        screenLifecycle = screenLifecycle,
        viewModel = viewModel,
        navController = navController
    )
    val stepper = viewModel.stepper.collectAsState(VROStateStep(viewModel.initialState)).value
    if (stepper is VROStateStep) content.ComposableContainer(stepper.state, viewModel, onDismiss)
}

@Composable
internal fun <VM : VROComposableDialogViewModel<S, E>, S : VROState, E : VROEvent> VroComposableBottomSheetContent(
    viewModel: VM,
    content: VROComposableBottomSheetContent<S, E>,
    initialState: S? = null,
    onDismiss: () -> Unit,
) {
    content.context = LocalContext.current
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(screenLifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = { viewModel.setInitialState(initialState) },
            onStart = { viewModel.onStart() },
            onResume = { viewModel.onResume() },
        )
        screenLifecycle.addObserver(observer)
        onDispose {
            screenLifecycle.removeObserver(observer)
        }
    }
    when (val stepper = viewModel.stepper.collectAsState(VROStateStep(viewModel.initialState)).value) {
        is VROSkeletonStep -> content.ComposableSkeleton()
        is VROStateStep -> content.ComposableContainer(stepper.state, viewModel, onDismiss)
        else -> Unit
    }
}