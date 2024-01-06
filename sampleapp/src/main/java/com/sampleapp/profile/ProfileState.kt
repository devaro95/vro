package com.sampleapp.profile

import com.vro.state.VROState

data class ProfileState(
    val userId: String,
) : VROState {
    companion object {
        val INITIAL = ProfileState(userId = "")
    }
}
