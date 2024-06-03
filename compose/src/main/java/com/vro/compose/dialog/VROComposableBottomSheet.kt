package com.vro.compose.dialog

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.vro.compose.lifecycleevent.createLifecycleEventObserver
import com.vro.event.VROEvent
import com.vro.state.VROState
import com.vro.state.VROStepper.VROStateStep

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
            onStart = { viewModel.startViewModel() },
            onResume = { viewModel.onResume() },
        )
        screenLifecycle.addObserver(observer)
        onDispose {
            screenLifecycle.removeObserver(observer)
        }
    }
    val stepper = viewModel.stepper.collectAsState(VROStateStep(viewModel.initialState)).value
    if (stepper is VROStateStep) content.ComposableContainer(stepper.state, viewModel, onDismiss)
}