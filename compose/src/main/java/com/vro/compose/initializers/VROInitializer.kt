package com.vro.compose.initializers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.vro.compose.VROComposableViewModel
import com.vro.core_android.navigation.VRONavigator
import com.vro.core_android.viewmodel.VROViewModelCore
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeNavigatorListener(
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