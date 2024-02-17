package com.sampleapp.dialog.bottomsheet

import com.vro.constants.EMPTY_STRING
import com.vro.state.VROState

data class SampleBottomSheetState(
    val name: String,
) : VROState {
    companion object {
        val INITIAL = SampleBottomSheetState(name = EMPTY_STRING)
    }
}