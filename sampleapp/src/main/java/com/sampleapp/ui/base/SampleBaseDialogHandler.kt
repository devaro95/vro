package com.sampleapp.ui.base

import androidx.compose.runtime.Composable
import com.sampleapp.dialog.*
import com.sampleapp.ui.base.SampleBaseViewModel.Companion.DIALOG_LOADING
import com.sampleapp.ui.base.SampleBaseViewModel.Companion.DIALOG_SIMPLE
import com.vro.compose.handler.VRODialogHandler
import com.vro.event.VROEvent
import com.vro.state.VRODialogData

open class SampleBaseDialogHandler<E : VROEvent> : VRODialogHandler<E>() {

    @Composable
    override fun OnDialog(data: VRODialogData) {
        when (data.type) {
            DIALOG_LOADING -> LoadingDialog()
            DIALOG_SIMPLE -> SampleSimpleDialog(data.value as SampleSimpleDialogData)
        }
    }
}