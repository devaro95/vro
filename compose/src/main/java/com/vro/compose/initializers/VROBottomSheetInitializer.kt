/**
 * Package containing initialization functions for bottom sheet components.
 */
package com.vro.compose.initializers

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.states.rememberSheetState
import com.vro.core_android.lifecycleevent.createLifecycleEventObserver
import com.vro.viewmodel.VRODialogViewModel
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.state.VROState
import com.vro.state.VROStepper
import com.vro.state.VROStepper.VRODialogStep
import com.vro.state.VROStepper.VROSkeletonStep
import com.vro.state.VROStepper.VROStateStep
import kotlinx.coroutines.launch

/**
 * Initializes a bottom sheet with ViewModel support.
 * Handles state management, lifecycle events, and content rendering.
 *
 * @param VM The ViewModel type that extends [VRODialogViewModel]
 * @param S The state type that extends [VROState]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance to manage state
 * @param initialState Optional initial state for the bottom sheet
 * @param listener Optional dialog listener for callbacks
 * @param modifier Modifier to apply to the bottom sheet
 * @param shape Shape of the bottom sheet
 * @param fullExpanded If true, skips partially expanded state
 * @param onDismiss Callback when the sheet is dismissed
 * @param containerColor Background color of the sheet
 * @param content The bottom sheet content composable
 *
 * @see ModalBottomSheet for base implementation
 * @see VROComposableViewModelBottomSheetContent for content requirements
 */
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

        val stepper = viewModel.stepper.collectAsStateWithLifecycle(
            initialValue =
                content.skeleton?.let {
                    VROSkeletonStep(viewModel.initialState)
                } ?: VROStateStep(viewModel.initialState),
            lifecycle = screenLifecycle
        ).value
        if (stepper is VROSkeletonStep) {
            content.ComposableSkeleton()
        } else {
            content.CreateDialog(
                state = stepper.state,
                events = viewModel,
                listener = listener,
                onDismiss = onDismiss
            )
            (stepper as? VRODialogStep)?.let {
                content.onDialog(it.dialogState)
            }
            (stepper as? VROStepper.VROErrorStep)?.let {
                content.onError(it.error, it.data)
            }
        }
    }
}

/**
 * Initializes a simple bottom sheet without ViewModel dependency.
 *
 * @param S The state type that extends [VROState]
 *
 * @param initialState The initial state for the bottom sheet
 * @param listener Optional dialog listener for callbacks
 * @param modifier Modifier to apply to the bottom sheet
 * @param shape Shape of the bottom sheet
 * @param fullExpanded If true, skips partially expanded state
 * @param onDismiss Callback when the sheet is dismissed
 * @param containerColor Background color of the sheet
 * @param content The bottom sheet content composable
 *
 * @see ModalBottomSheet for base implementation
 * @see VROComposableBottomSheetContent for content requirements
 */
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