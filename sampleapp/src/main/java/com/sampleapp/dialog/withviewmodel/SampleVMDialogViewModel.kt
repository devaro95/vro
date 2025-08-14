package com.sampleapp.dialog.withviewmodel

import com.sampleapp.ui.home.SampleHomeViewModel
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
        checkDataState().also {
            updateScreen {
                copy(
                    buttonText = when (it.buttonText) {
                        FIRST_TEXT -> SECOND_TEXT
                        SECOND_TEXT -> FIRST_TEXT
                        else -> FIRST_TEXT
                    }
                )
            }
        }
    }

    companion object {
        const val FIRST_TEXT = "Press to update"
        const val SECOND_TEXT = "Press again"
    }
}