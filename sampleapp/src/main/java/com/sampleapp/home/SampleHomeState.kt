package com.sampleapp.home

import com.vro.state.VROState

data class SampleHomeState(
    val text: String,
) : VROState {
    companion object {
        val INITIAL = SampleHomeState(text = "")
    }
}