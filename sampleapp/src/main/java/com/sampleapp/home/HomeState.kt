package com.sampleapp.home

import com.vro.state.VROState

data class HomeState(
    val text: String,
) : VROState {
    companion object {
        val INITIAL = HomeState(text = "")
    }
}