package com.vro.fragment.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROScreen<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> {

    lateinit var state: State<S>

    @Composable
    fun InitializeState(viewModel: VM, initialState: S?) {
        state = viewModel.state.collectAsState(initialState ?: viewModel.initialViewState)
    }

    @Composable
    fun CreateScreen(viewModel: VM) {
        viewModel.onStart()
        CreateContent(state.value, viewModel)
    }

    @Composable
    abstract fun CreateContent(
        state: S,
        eventListener: VROEventListener<E>,
    )

    @VROMultiDevicePreview
    @Composable
    abstract fun CreatePreview()
}
