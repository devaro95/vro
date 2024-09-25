package com.vro.compose.initializers

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.states.rememberSheetState
import com.vro.core_android.lifecycleevent.createLifecycleEventObserver
import com.vro.core_android.viewmodel.VRODialogViewModel
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.state.VROState
import com.vro.state.VROStepper.VROSkeletonStep
import com.vro.state.VROStepper.VROStateStep
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <VM : VRODialogViewModel<S, E>, S : VROState, E : VROEvent> InitializeViewModelBottomSheet(
    viewModel: VM,
    initialState: S?,
    listener: VRODialogListener?,
    modifier: Modifier,
    shape: Shape,
    fullExpanded: Boolean,
    onDismiss: () -> Unit,
    containerColor: Color,
    content: VROComposableViewModelBottomSheetContent<S, E>,
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = fullExpanded)
    val sheetStateVro = rememberSheetState()
    sheetStateVro.setHideActionListener {
        coroutineScope.launch {
            sheetState.hide()
            onDismiss()
        }
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = shape,
        modifier = Modifier
            .then(modifier)
            .statusBarsPadding(),
        containerColor = containerColor
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
            is VROStateStep -> content.CreateDialog(stepper.state, viewModel, listener, onDismiss)
            else -> Unit
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <S : VROState> InitializeBottomSheet(
    initialState: S,
    listener: VRODialogListener?,
    modifier: Modifier,
    shape: Shape,
    fullExpanded: Boolean,
    onDismiss: () -> Unit,
    containerColor: Color,
    content: VROComposableBottomSheetContent<S>,
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = fullExpanded)
    val sheetStateVro = rememberSheetState()
    sheetStateVro.setHideActionListener {
        coroutineScope.launch {
            sheetState.hide()
            onDismiss()
        }
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = shape,
        modifier = Modifier
            .then(modifier)
            .statusBarsPadding(),
        containerColor = containerColor
    ) {
        content.context = LocalContext.current
        content.CreateDialog(initialState, listener, onDismiss)
    }
}