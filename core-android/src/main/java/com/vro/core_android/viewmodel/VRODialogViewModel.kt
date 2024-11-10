package com.vro.core_android.viewmodel

import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.state.VROState

abstract class VRODialogViewModel<S : VROState, E : VROEvent> : VROViewModelCore<S, E>() {

    fun setInitialState(initialState: S? = null) {
        initialState?.let {
            screenState = it
            updateScreen { screenState }
        }
    }

    override fun doBack(result: VROBackResult?) = Unit
}