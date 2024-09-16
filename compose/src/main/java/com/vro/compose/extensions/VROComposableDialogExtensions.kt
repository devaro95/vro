package com.vro.compose.extensions

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.vro.compose.dialog.VROComposableDialogContent
import com.vro.core_android.lifecycleevent.createLifecycleEventObserver
import com.vro.core_android.viewmodel.VRODialogViewModel
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.state.VROState
import com.vro.state.VROStepper

@Composable
fun <VM : VRODialogViewModel<S, E>, S : VROState, E : VROEvent> VROComposableDialog(
    viewModel: @Composable () -> VM,
    content: VROComposableDialogContent<S, E>,
    initialState: S? = null,
    listener: VRODialogListener? = null,
    onDismiss: () -> Unit,
) {
    VroComposableDialogContent(
        viewModel = viewModel.invoke(),
        content = content,
        onDismiss = onDismiss,
        listener = listener
    )
}

@Composable
private fun <VM : VRODialogViewModel<S, E>, S : VROState, E : VROEvent> VroComposableDialogContent(
    viewModel: VM,
    content: VROComposableDialogContent<S, E>,
    listener: VRODialogListener?,
    onDismiss: () -> Unit,
) {
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(screenLifecycle) {
        val observer = createLifecycleEventObserver(
            onStart = { viewModel.onStart() },
            onResume = { viewModel.onResume() }
        )
        screenLifecycle.addObserver(observer)
        onDispose {
            screenLifecycle.removeObserver(observer)
        }
    }
    when (val stepper = viewModel.stepper.collectAsState(VROStepper.VROStateStep(viewModel.initialState)).value) {
        is VROStepper.VROSkeletonStep -> content.ComposableDialogSkeleton()
        is VROStepper.VROStateStep -> content.CreateDialog(stepper.state, viewModel, listener, onDismiss)
        is VROStepper.VROErrorStep -> content.OnError(stepper.error, stepper.data)
        else -> Unit
    }
}
