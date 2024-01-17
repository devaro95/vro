package com.vro.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.vro.compose.extensions.createViewModel
import com.vro.compose.preview.VROMultiDevicePreview
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROFragmentScreen<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> {

    lateinit var viewModel: VM

    @Composable
    fun CreateScreen(initialState: S?, viewModelSeed: VM) {
        InitializeScreen(initialState, viewModelSeed)
    }

    @Composable
    abstract fun ComposableContent(state: S)

    @VROMultiDevicePreview
    @Composable
    abstract fun ComposablePreview()

    fun viewModelEvent(event: E) {
        viewModel.eventListener(event)
    }

    @Composable
    private fun InitializeScreen(initialState: S?, viewModelSeed: VM) {
        viewModel = createViewModel(remember { viewModelSeed })
        LaunchedEffect(Unit) {
            viewModel.onStart()
        }
        val state by viewModel.state.collectAsState(initialState ?: viewModel.initialViewState)
        ComposableContent(state)
    }
}
