package com.sampleapp.detail

import com.vro.constants.EMPTY_STRING
import com.vro.state.VROState

data class SampleDetailState(
    val userId: String,
) : VROState {
    companion object {
        val INITIAL = SampleDetailState(userId = EMPTY_STRING)
    }
}
