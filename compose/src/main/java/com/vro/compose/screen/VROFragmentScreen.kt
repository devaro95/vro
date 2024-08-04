package com.vro.compose.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.vro.compose.extensions.createViewModel
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper

abstract class VROFragmentScreen<S : VROState, D : VRODestination, E : VROEvent> :
    VROScreenBuilder<S, E>() {

    @Composable
    fun CreateScreen(viewModelSeed: VROViewModel<S, D, E>) {
        val viewModel = createViewModel(remember { viewModelSeed })
        LaunchedEffect(Unit) {
            viewModel.onStart()
        }
        when (val stepper =
            viewModel.stepper.collectAsState(VROStepper.VROStateStep(viewModel.initialViewState)).value) {
            is VROStepper.VROSkeletonStep -> Unit
            is VROStepper.VROStateStep -> ComposableScreenContainer(stepper.state)
            is VROStepper.VROErrorStep -> OnError(stepper.error, stepper.data)
            is VROStepper.VRODialogStep -> {
                ComposableScreenContainer(stepper.state)
                OnDialog(stepper.dialogState)
            }
        }
    }
}
