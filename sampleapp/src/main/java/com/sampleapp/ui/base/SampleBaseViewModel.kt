package com.sampleapp.ui.base

import com.sampleapp.dialog.SampleSimpleDialogData
import com.vro.event.VROEvent
import com.vro.compose.viewmodel.VROComposableViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogState
import com.vro.state.VROState

abstract class SampleBaseViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROComposableViewModel<S, D, E>() {

    fun showLoading() {
        updateDialog(VRODialogState(DIALOG_LOADING))
    }

    fun showSimpleDialog() {
        updateDialog(
            VRODialogState(
                DIALOG_SIMPLE,
                SampleSimpleDialogData(
                    onButtonClick = ::hideSimpleDialog
                )
            )
        )
    }

    private fun hideSimpleDialog() {
        updateState()
    }

    companion object {
        const val DIALOG_LOADING = 1000
        const val DIALOG_SIMPLE = 1001
    }
}