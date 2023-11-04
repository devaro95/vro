package com.vro.fragment.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROScreen<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> {

    @Composable
    fun CreateScreen(viewModel: VM) {
        val state by viewModel.state.collectAsState()
        viewModel.onStart()
        CreateContent(state, viewModel)
    }

    @Composable
    abstract fun CreateContent(state: S, viewModel: VM)

    @VROMultiDevicePreview
    @Composable
    abstract fun CreatePreview()
}
