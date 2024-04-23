package com.sampleapp.dialog.bottomsheet

import com.vro.constants.EMPTY_STRING
import com.vro.state.VROState

data class SampleBottomSheetState(
    val buttonText: String,
) : VROState {
    companion object {
        val INITIAL = SampleBottomSheetState(buttonText = EMPTY_STRING)
    }
}