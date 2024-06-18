package com.sampleapp.ui.profile

import com.vro.constants.EMPTY_STRING
import com.vro.state.VROState

data class SampleProfileState(
    val userId: String,
) : VROState {
    companion object {
        val INITIAL = SampleProfileState(userId = EMPTY_STRING)
    }
}
