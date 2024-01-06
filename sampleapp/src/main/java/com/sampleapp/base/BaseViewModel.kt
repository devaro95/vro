package com.sampleapp.base

import com.vro.dialog.VRODialogState
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class BaseViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROViewModel<S, D, E>() {

    fun showLoading() {
        updateDialogState(VRODialogState(DIALOG_LOADING))
    }

    companion object {
        const val DIALOG_LOADING = 1000
    }
}