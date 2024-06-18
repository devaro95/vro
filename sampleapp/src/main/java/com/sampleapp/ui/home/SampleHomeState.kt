package com.sampleapp.ui.home

import com.vro.constants.EMPTY_STRING
import com.vro.state.VROState

data class SampleHomeState(
    val text: String,
) : VROState {
    companion object {
        val INITIAL = SampleHomeState(text = EMPTY_STRING)
    }
}