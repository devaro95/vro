/**
 * Package containing extension functions for bottom sheet components.
 */
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
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.vro.compose.navigator.VROComposableNavigator
import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.initializers.*
import com.vro.core_android.viewmodel.VRODialogViewModel
import com.vro.core_android.viewmodel.VROViewModel
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper.*

/**
 * Adds a bottom sheet destination to the navigation graph with ViewModel support.
 *
 * @param VM The ViewModel type that extends [VROViewModel]
 * @param S The state type that extends [VROState]
 * @param E The event type that extends [VROEvent]
 * @param D The navigation destination type that extends [VRODestination]
 *
 * @param viewModel Factory function for the ViewModel
 * @param content The bottom sheet content composable
 * @param navigator The navigation controller
 * @param listener Optional dialog listener for callbacks
 * @param onDismiss Callback when the sheet is dismissed
 *
 * @see VroComposableNavBottomSheetContent For internal implementation
 * @see bottomSheet For base navigation implementation
 */
@OptIn(ExperimentalMaterialNavigationApi::class)
fun <VM : VROViewModel<S, D, E>, S : VROState, E : VROEvent, D : VRODestination> NavGraphBuilder.vroBottomSheet(
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
            viewModel = viewModel(),
            content = content,
            navigator = navigator,
            listener = listener,
            onDismiss = onDismiss
        )
    }
}

/**
 * Adds a bottom sheet destination to the navigation graph without ViewModel support.
 *
 * @param S The state type that extends [VROState]
 *
 * @param initialState The initial state to be used by the bottom sheet
 * @param content The bottom sheet content composable
 * @param listener Optional dialog listener for callbacks
 * @param onDismiss Callback when the sheet is dismissed
 *
 * @see VroComposableBottomSheetContent For internal implementation
 * @see bottomSheet For base navigation implementation
 */
@OptIn(ExperimentalMaterialNavigationApi::class)
fun <S : VROState> NavGraphBuilder.vroBottomSheet(
    initialState: S,
    content: VROComposableBottomSheetContent<S>,
    listener: VRODialogListener? = null,
    onDismiss: () -> Unit = { },
) {
    bottomSheet(
        route = content.destinationRoute(),
    ) {
        VroComposableBottomSheetContent(
            content = content,
            listener = listener,
            onDismiss = onDismiss,
            initialState = initialState
        )
    }
}

/**
 * Composable function for a ViewModel-backed bottom sheet.
 * Provides full configuration options for appearance and behavior.
 *
 * @param VM The ViewModel type that extends [VRODialogViewModel]
 * @param S The state type that extends [VROState]
 * @param E The event type that extends [VROEvent]
 *
 * @param modifier Modifier to apply to the bottom sheet
 * @param viewModel Factory function for the ViewModel
 * @param content The bottom sheet content composable
 * @param initialState Optional initial state for the sheet
 * @param onDismiss Callback when the sheet is dismissed
 * @param fullExpanded If true, skips partially expanded state
 * @param shape Shape of the bottom sheet
 * @param containerColor Background color of the sheet
 * @param listener Optional dialog listener for callbacks
 *
 * @see InitializeViewModelBottomSheet For initialization logic
 */
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
        viewModel = viewModel(),
        initialState = initialState,
        listener = listener,
        shape = shape,
        fullExpanded = fullExpanded,
        onDismiss = onDismiss,
        containerColor = containerColor,
        content = content
    )
    InitializeEventsListener(viewModel())
}

/**
 * Composable function for a simple bottom sheet without ViewModel dependency.
 *
 * @param S The state type that extends [VROState]
 *
 * @param modifier Modifier to apply to the bottom sheet
 * @param content The bottom sheet content composable
 * @param initialState The initial state for the sheet
 * @param onDismiss Callback when the sheet is dismissed
 * @param fullExpanded If true, skips partially expanded state
 * @param shape Shape of the bottom sheet
 * @param containerColor Background color of the sheet
 * @param listener Optional dialog listener for callbacks
 *
 * @see InitializeBottomSheet For initialization logic
 */
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

/**
 * Internal implementation for navigation-based bottom sheet content.
 * Handles lifecycle, state management, and content rendering.
 *
 * @param VM The ViewModel type that extends [VROViewModel]
 * @param S The state type that extends [VROState]
 * @param E The event type that extends [VROEvent]
 * @param D The navigation destination type that extends [VRODestination]
 *
 * @param viewModel The ViewModel instance
 * @param content The bottom sheet content composable
 * @param navigator The navigation controller
 * @param listener Optional dialog listener for callbacks
 * @param onDismiss Callback when the sheet is dismissed
 *
 * @see InitializeLifecycleObserver For lifecycle handling
 * @see InitializeEventsListener For event handling
 * @see InitializeNavigatorListener For navigation handling
 */
@Composable
internal fun <VM : VROViewModel<S, D, E>, S : VROState, E : VROEvent, D : VRODestination> VroComposableNavBottomSheetContent(
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
    val stepper = viewModel.stepper.collectAsStateWithLifecycle(
        initialValue = content.skeleton?.let {
            VROSkeletonStep(viewModel.initialState)
        } ?: VROStateStep(viewModel.initialState),
        lifecycle = screenLifecycle
    ).value

    if (stepper is VROSkeletonStep) {
        content.ComposableDialogSkeleton()
    } else {
        content.CreateDialog(stepper.state, viewModel, listener, onDismiss)
        (stepper as? VRODialogStep)?.let {
            content.onDialog(it.dialogState)
        }
        (stepper as? VROErrorStep)?.let {
            content.onError(it.error, it.data)
        }
    }
    InitializeEventsListener(viewModel)
    InitializeNavigatorListener(
        viewModel = viewModel,
        navigator = navigator
    )
}

/**
 * Internal implementation for simple bottom sheet content without navigation or ViewModel.
 * Responsible for rendering the dialog content and assigning the context.
 *
 * @param S The state type that extends [VROState]
 *
 * @param initialState The initial state of the bottom sheet
 * @param content The content to be displayed in the bottom sheet
 * @param listener Optional dialog listener for callbacks
 * @param onDismiss Callback when the sheet is dismissed
 */
@Composable
internal fun <S : VROState> VroComposableBottomSheetContent(
    initialState: S,
    content: VROComposableBottomSheetContent<S>,
    listener: VRODialogListener?,
    onDismiss: () -> Unit,
) {
    content.context = LocalContext.current
        content.CreateDialog(initialState, listener, onDismiss)
}