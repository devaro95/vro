package com.sampleapp.base

import androidx.compose.runtime.Composable
import com.sampleapp.base.SampleBaseViewModel.Companion.DIALOG_LOADING
import com.sampleapp.dialog.LoadingDialog
import com.vro.compose.VROComposableDialog
import com.vro.compose.VROComposableScreenContent
import com.vro.compose.VROComposableViewModel
import com.vro.dialog.VRODialogState
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class SampleBaseScreen<S : VROState, D : VRODestination, E : VROEvent> :
    VROComposableScreenContent<S, D, E>() {

    @Composable
    override fun OnDialog(data: VRODialogState) {
        when (data.type) {
            DIALOG_LOADING -> VROComposableDialog { LoadingDialog() }
        }
    }
}