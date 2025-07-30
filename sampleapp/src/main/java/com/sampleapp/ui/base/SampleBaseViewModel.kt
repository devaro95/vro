package com.sampleapp.ui.base

import com.sampleapp.dialog.SampleSimpleDialogData
import com.vro.viewmodel.VROViewModel
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogData
import com.vro.state.VROState

abstract class SampleBaseViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROViewModel<S, D, E>() {

    fun showLoading() {
        updateDialog(VRODialogData(DIALOG_LOADING))
    }

    fun showSimpleDialog() {
        updateDialog(
            VRODialogData(
                DIALOG_SIMPLE,
                SampleSimpleDialogData(
                    onButtonClick = ::hideSimpleDialog
                )
            )
        )
    }

    private fun hideSimpleDialog() {
        updateScreen()
    }

    companion object {
        const val DIALOG_LOADING = 1000
        const val DIALOG_SIMPLE = 1001
    }
}