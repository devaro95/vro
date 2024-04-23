package com.vro.compose.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.vro.compose.extensions.createViewModel
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper.VRODialogStep
import com.vro.state.VROStepper.VROStateStep

abstract class VROFragmentScreen<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> : VROScreenBuilder<S,E>() {

    @Composable
    fun CreateScreen(viewModelSeed: VM) {
        val viewModel = createViewModel(remember { viewModelSeed })
        LaunchedEffect(Unit) {
            viewModel.onStart()
        }
        when (val stepper = viewModel.stepper.collectAsState(VROStateStep(viewModel.initialViewState)).value) {
            is VROStateStep -> ComposableSectionContainer(stepper.state, viewModel)
            is VRODialogStep -> {
                ComposableSectionContainer(stepper.state, viewModel)
                OnDialog(stepper.dialogState)
            }
        }
    }
}
