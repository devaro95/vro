package com.sampleapp.ui.samplefragment

import com.sampleapp.ui.samplefragment.SampleComposableFragmentViewModel.Companion.FIRST_TEXT
import com.vro.state.VROState

data class SampleComposableFragmentState(
    val text: String,
) : VROState {
    companion object {
        val INITIAL = SampleComposableFragmentState(text = FIRST_TEXT)
    }
}