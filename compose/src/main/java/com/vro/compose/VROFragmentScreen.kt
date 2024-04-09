package com.vro.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.vro.compose.extensions.createViewModel
import com.vro.compose.preview.VROMultiDevicePreview
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogState
import com.vro.state.VROState
import com.vro.state.VROStepper.*

abstract class VROFragmentScreen<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> {

    lateinit var viewModel: VM

    @Composable
    fun CreateScreen(viewModelSeed: VM) {
        InitializeScreen(viewModelSeed)
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
    private fun InitializeScreen(viewModelSeed: VM) {
        viewModel = createViewModel(remember { viewModelSeed })
        LaunchedEffect(Unit) {
            viewModel.onStart()
        }
        when (val stepper = viewModel.stepper.collectAsState(VROStateStep(viewModel.initialViewState)).value) {
            is VROStateStep -> ComposableContent(stepper.state)
            is VRODialogStep -> {
                ComposableContent(stepper.state)
                OnDialog(stepper.dialogState)
            }
        }
    }

    @Composable
    open fun OnDialog(data: VRODialogState) = Unit
}
