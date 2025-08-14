package com.sampleapp.dialog.withviewmodel

import com.vro.viewmodel.VRODialogViewModel
import com.vro.navigation.VROBackResult

class SampleVMDialogViewModel : VRODialogViewModel<SampleVMDialogState, SampleVMDialogEvents>() {

    override val initialState: SampleVMDialogState = SampleVMDialogState.INITIAL

    override fun doBack(result: VROBackResult?) {

    }

    override fun onEvent(event: SampleVMDialogEvents) {
        when (event) {
            SampleVMDialogEvents.TextChange -> onTextChange()
        }
    }

    private fun onTextChange() {
        updateScreen {
            copy(
                buttonText = "New Text"
            )
        }
    }
}