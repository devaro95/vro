package com.sampleapp.ui.detail

import com.vro.constants.EMPTY_STRING
import com.vro.state.VROState

data class SampleDetailState(
    val text: String,
) : VROState {
    companion object {
        val INITIAL = SampleDetailState(text = EMPTY_STRING)
    }
}
