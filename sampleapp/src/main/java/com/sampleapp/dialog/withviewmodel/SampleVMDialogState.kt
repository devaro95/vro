package com.sampleapp.dialog.withviewmodel

import com.sampleapp.dialog.withviewmodel.SampleVMDialogViewModel.Companion.FIRST_TEXT
import com.vro.state.VROState

data class SampleVMDialogState(
    val buttonText: String,
) : VROState {
    companion object {
        val INITIAL = SampleVMDialogState(
            buttonText = FIRST_TEXT
        )
    }
}