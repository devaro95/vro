package com.vro.compose.initializers

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vro.compose.dialog.VROComposableDialogContent
import com.vro.core_android.lifecycleevent.createLifecycleEventObserver
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.state.VROState
import com.vro.state.VROStepper
import com.vro.viewmodel.VRODialogViewModel

@Composable
fun <VM : VRODialogViewModel<S, E>, S : VROState, E : VROEvent> InitializeDialog(
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
        onDispose { screenLifecycle.removeObserver(observer) }
    }
    val stepper = viewModel.stepper.collectAsStateWithLifecycle(
        initialValue = content.skeleton?.let { VROStepper.VROSkeletonStep(viewModel.initialState) } ?: VROStepper.VROStateStep(viewModel.initialState),
        lifecycle = screenLifecycle
    ).value
    if (stepper is VROStepper.VROSkeletonStep) content.ComposableSkeleton()
    else {
        content.CreateDialog(stepper.state, viewModel, listener, onDismiss)
        (stepper as? VROStepper.VRODialogStep)?.let { content.onDialog(it.dialogState) }
        (stepper as? VROStepper.VROErrorStep)?.let { content.onError(it.error, it.data) }
    }
}
