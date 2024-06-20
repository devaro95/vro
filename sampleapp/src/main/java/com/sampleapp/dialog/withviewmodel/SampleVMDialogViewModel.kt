package com.sampleapp.dialog.withviewmodel

import com.vro.compose.dialog.VROComposableDialogViewModel
import com.vro.navigation.VROBackResult

class SampleVMDialogViewModel : VROComposableDialogViewModel<SampleVMDialogState, SampleVMDialogEvents>() {

    override val initialState: SampleVMDialogState = SampleVMDialogState.INITIAL

    override fun eventBack(result: VROBackResult?) {

    }

    override fun eventListener(event: SampleVMDialogEvents) {
        when (event) {
            SampleVMDialogEvents.TextChange -> onTextChange()
        }
    }

    private fun onTextChange() {
        updateState {
            copy(
                buttonText = "New Text"
            )
        }
    }
}