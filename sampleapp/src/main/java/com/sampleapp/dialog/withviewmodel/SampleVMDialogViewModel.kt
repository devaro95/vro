package com.sampleapp.dialog.withviewmodel

import com.vro.core_android.viewmodel.VRODialogViewModel
import com.vro.navigation.VROBackResult

class SampleVMDialogViewModel : VRODialogViewModel<SampleVMDialogState, SampleVMDialogEvents>() {

    override val initialState: SampleVMDialogState = SampleVMDialogState.INITIAL

    override fun eventBack(result: VROBackResult?) {

    }

    override fun eventListener(event: SampleVMDialogEvents) {
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