package com.sampleapp.ui.samplefragment.destination

import com.sampleapp.ui.samplefragment.SampleComposableFragmentViewModel.Companion.FIRST_TEXT
import com.vro.state.VROState

data class SampleComposableDestinationFragmentState(
    val text: String,
) : VROState {
    companion object {
        val INITIAL = SampleComposableDestinationFragmentState(text = FIRST_TEXT)
    }
}