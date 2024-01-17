package com.sampleapp.base

import com.vro.compose.VROComposableViewModel
import com.vro.dialog.VRODialogState
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class SampleBaseViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROComposableViewModel<S, D, E>() {

    fun showLoading() {
        updateDialogState(VRODialogState(DIALOG_LOADING))
    }

    companion object {
        const val DIALOG_LOADING = 1000
    }
}