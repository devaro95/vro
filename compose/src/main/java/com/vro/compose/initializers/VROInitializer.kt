package com.vro.compose.initializers

/**
 * Package containing initialization functions for navigation and event listeners.
 */
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.vro.core_android.navigation.VRONavigator
import com.vro.viewmodel.VROViewModel
import com.vro.viewmodel.VROViewModelCore
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

/**
 * Initializes and manages navigation state observation.
 * Listens for navigation events from the ViewModel and delegates to the navigator.
 * Handles both forward navigation and back navigation with results.
 *
 * @param VM The ViewModel type that extends [VROViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance to observe for navigation events
 * @param navigator The navigator implementation to handle navigation commands
 *
 * @see VRONavigator For navigation interface requirements
 * @see VROViewModel.getNavigationState For expected ViewModel interface
 *
 */
@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeNavigatorListener(
    viewModel: VM,
    navigator: VRONavigator<D>,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getNavigationState().collect {
            it?.destination?.let { destination ->
                if (!destination.isNavigated) {
                    navigator.navigate(destination)
                    destination.setNavigated()
                }
            } ?: navigator.navigateBack(it?.backResult)
        }
    }
}

/**
 * Initializes and manages event observation.
 * Listens for events from the ViewModel and delegates to the event handler.
 *
 * @param VM The ViewModel type that extends [VROViewModelCore]
 * @param S The state type that extends [VROState]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance to observe for events
 *
 * @see VROViewModelCore.eventObservable For expected ViewModel interface
 * @see VROViewModelCore.onEvent For event handling requirements
 *
 */
@Composable
fun <VM : VROViewModelCore<S, E>, S : VROState, E : VROEvent> InitializeEventsListener(
    viewModel: VM,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventObservable.collect {
            viewModel.onEvent(it)
        }
    }
}