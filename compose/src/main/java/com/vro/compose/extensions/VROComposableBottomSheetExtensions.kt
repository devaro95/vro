package com.vro.compose.extensions

import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableViewModel
import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.initializers.*
import com.vro.core_android.viewmodel.VRODialogViewModel
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper.VROStateStep

@OptIn(ExperimentalMaterialNavigationApi::class)
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, E : VROEvent, D : VRODestination> NavGraphBuilder.vroBottomSheet(
    viewModel: @Composable () -> VM,
    content: VROComposableViewModelBottomSheetContent<S, E>,
    navigator: VROComposableNavigator<D>,
    listener: VRODialogListener? = null,
    onDismiss: () -> Unit = { },
) {
    bottomSheet(
        route = content.destinationRoute(),
    ) {
        VroComposableNavBottomSheetContent(
            viewModel = viewModel.invoke(),
            content = content,
            navigator = navigator,
            listener = listener,
            onDismiss = onDismiss
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <VM : VRODialogViewModel<S, E>, S : VROState, E : VROEvent> VroBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: @Composable () -> VM,
    content: VROComposableViewModelBottomSheetContent<S, E>,
    initialState: S? = null,
    onDismiss: () -> Unit,
    fullExpanded: Boolean = false,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = Color.White,
    listener: VRODialogListener? = null,
) {
    InitializeViewModelBottomSheet(
        modifier = modifier,
        viewModel = viewModel.invoke(),
        initialState = initialState,
        listener = listener,
        shape = shape,
        fullExpanded = fullExpanded,
        onDismiss = onDismiss,
        containerColor = containerColor,
        content = content
    )
    InitializeEventsListener(viewModel.invoke())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <S : VROState> VroBottomSheet(
    modifier: Modifier = Modifier,
    content: VROComposableBottomSheetContent<S>,
    initialState: S,
    onDismiss: () -> Unit,
    fullExpanded: Boolean = false,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = Color.White,
    listener: VRODialogListener? = null,
) {
    InitializeBottomSheet(
        modifier = modifier,
        initialState = initialState,
        listener = listener,
        shape = shape,
        fullExpanded = fullExpanded,
        onDismiss = onDismiss,
        containerColor = containerColor,
        content = content
    )
}

@Composable
internal fun <VM : VROComposableViewModel<S, D, E>, S : VROState, E : VROEvent, D : VRODestination> VroComposableNavBottomSheetContent(
    viewModel: VM,
    content: VROComposableViewModelBottomSheetContent<S, E>,
    navigator: VROComposableNavigator<D>,
    listener: VRODialogListener?,
    onDismiss: () -> Unit,
) {
    content.context = LocalContext.current
    val screenLifecycle = LocalLifecycleOwner.current.lifecycle
    InitializeLifecycleObserver(
        screenLifecycle = screenLifecycle,
        viewModel = viewModel,
        navController = navigator.navController
    )
    val stepper = viewModel.stepper.collectAsStateWithLifecycle(VROStateStep(viewModel.initialState)).value
    content.CreateDialog(stepper.state, viewModel, listener, onDismiss)
    InitializeEventsListener(viewModel)
    InitializeNavigatorListener(
        viewModel = viewModel,
        navigator = navigator
    )
}