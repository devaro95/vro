package com.sampleapp.dialog.withviewmodel

import com.vro.state.VROState

data class SampleVMDialogState(
    val buttonText: String,
) : VROState {
    companion object {
        val INITIAL = SampleVMDialogState(
            buttonText = "Text Dialog"
        )
    }
}