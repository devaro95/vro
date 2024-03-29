package com.sampleapp.base

import androidx.compose.runtime.Composable
import com.sampleapp.base.SampleBaseViewModel.Companion.DIALOG_LOADING
import com.sampleapp.base.SampleBaseViewModel.Companion.DIALOG_SIMPLE
import com.sampleapp.dialog.LoadingDialog
import com.sampleapp.dialog.SampleSimpleDialogData
import com.sampleapp.dialog.SimpleDialog
import com.vro.compose.VROComposableScreenContent
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogState
import com.vro.state.VROState

abstract class SampleBaseScreen<S : VROState, D : VRODestination, E : VROEvent> :
    VROComposableScreenContent<S, D, E>() {

    @Composable
    override fun OnDialog(data: VRODialogState) {
        when (data.type) {
            DIALOG_LOADING -> LoadingDialog()
            DIALOG_SIMPLE -> SimpleDialog(data.value as SampleSimpleDialogData)
        }
    }
}