package com.sampleapp.ui.base

import androidx.compose.runtime.Composable
import com.sampleapp.ui.base.SampleBaseViewModel.Companion.DIALOG_LOADING
import com.sampleapp.ui.base.SampleBaseViewModel.Companion.DIALOG_SIMPLE
import com.sampleapp.dialog.LoadingDialog
import com.sampleapp.dialog.SampleSimpleDialogData
import com.sampleapp.dialog.SampleSimpleDialog
import com.vro.compose.screen.VROScreen
import com.vro.event.VROEvent
import com.vro.state.VRODialogState
import com.vro.state.VROState

abstract class SampleBaseScreen<S : VROState, E : VROEvent> : VROScreen<S, E>() {

    @Composable
    override fun OnDialog(data: VRODialogState) {
        when (data.type) {
            DIALOG_LOADING -> LoadingDialog()
            DIALOG_SIMPLE -> SampleSimpleDialog(data.value as SampleSimpleDialogData)
        }
    }
}