package com.sampleapp.base

import androidx.compose.runtime.Composable
import com.sampleapp.base.SampleBaseViewModel.Companion.DIALOG_LOADING
import com.sampleapp.base.SampleBaseViewModel.Companion.DIALOG_SIMPLE
import com.sampleapp.dialog.LoadingDialog
import com.sampleapp.dialog.SampleSimpleDialogData
import com.sampleapp.dialog.SampleSimpleDialog
import com.vro.compose.VROComposableScreenContent
import com.vro.compose.model.VROOrientation
import com.vro.compose.model.VROOrientation.VERTICAL
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogState
import com.vro.state.VROState

abstract class SampleBaseScreen<S : VROState, D : VRODestination, E : VROEvent>(
    orientation: VROOrientation = VERTICAL
) : VROComposableScreenContent<S, D, E>(orientation) {

    @Composable
    override fun OnDialog(data: VRODialogState) {
        when (data.type) {
            DIALOG_LOADING -> LoadingDialog()
            DIALOG_SIMPLE -> SampleSimpleDialog(data.value as SampleSimpleDialogData)
        }
    }
}