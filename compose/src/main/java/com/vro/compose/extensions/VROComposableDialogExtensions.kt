package com.vro.compose.extensions

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.vro.compose.dialog.VROComposableDialogContent
import com.vro.compose.dialog.VROComposableDialogViewModel
import com.vro.compose.lifecycleevent.createLifecycleEventObserver
import com.vro.event.VROEvent
import com.vro.state.VROState
import com.vro.state.VROStepper

@Composable
fun <VM : VROComposableDialogViewModel<S, E>, S : VROState, E : VROEvent> VROComposableDialog(
    viewModel: @Composable () -> VM,
    content: VROComposableDialogContent<S, E>,
    initialState: S? = null,
    onDismiss: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    VroComposableDialogContent(
        viewModel = viewModel.invoke(),
        content = content,
        initialState = initialState,
        onDismiss = onDismiss,
        dismissOnBackPress = dismissOnBackPress,
        dismissOnClickOutside = dismissOnClickOutside,
    )
}

@Composable
private fun <VM : VROComposableDialogViewModel<S, E>, S : VROState, E : VROEvent> VroComposableDialogContent(
    viewModel: VM,
    content: VROComposableDialogContent<S, E>,
    initialState: S? = null,
    onDismiss: () -> Unit,
    dismissOnBackPress: Boolean,
    dismissOnClickOutside: Boolean,
) {
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(screenLifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = { viewModel.setInitialState(initialState) },
            onStart = { viewModel.startViewModel() },
            onResume = { viewModel.onResume() }
        )
        screenLifecycle.addObserver(observer)
        onDispose {
            screenLifecycle.removeObserver(observer)
        }
    }
    when (val stepper = viewModel.stepper.collectAsState(VROStepper.VROStateStep(viewModel.initialState)).value) {
        is VROStepper.VROStateStep -> content.CreateDialog(stepper.state, viewModel, onDismiss, dismissOnBackPress, dismissOnClickOutside)
        is VROStepper.VRODialogStep -> {
            content.CreateDialog(stepper.state, viewModel, onDismiss, dismissOnBackPress, dismissOnClickOutside)
        }
    }
}
