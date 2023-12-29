package com.vro.fragment.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROScreen<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> {

    private lateinit var viewModel: VM

    @Composable
    fun InitializeScreen(viewModel: VM, initialState: S?) {
        this.viewModel = viewModel
        LaunchedEffect(Unit) {
            viewModel.onStart()
        }
        val state by viewModel.state.collectAsState(initialState ?: viewModel.initialViewState)
        ComposableContent(state)
    }

    @Composable
    abstract fun ComposableContent(state: S)

    @VROMultiDevicePreview
    @Composable
    abstract fun ComposablePreview()

    fun viewModelEvent(event: E) {
        viewModel.eventListener(event)
    }
}
