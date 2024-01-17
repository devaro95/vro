package com.sampleapp.profile

import com.vro.state.VROState

data class SampleProfileState(
    val userId: String,
) : VROState {
    companion object {
        val INITIAL = SampleProfileState(userId = "")
    }
}
