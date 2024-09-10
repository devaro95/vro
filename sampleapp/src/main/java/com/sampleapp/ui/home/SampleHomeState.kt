package com.sampleapp.ui.home

import com.sampleapp.ui.home.SampleHomeViewModel.Companion.FIRST_TEXT
import com.vro.constants.EMPTY_STRING
import com.vro.state.VROState

data class SampleHomeState(
    val text: String,
) : VROState {
    companion object {
        val INITIAL = SampleHomeState(text = FIRST_TEXT)
    }
}