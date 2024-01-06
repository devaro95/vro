package com.sampleapp.base

import androidx.compose.runtime.Composable
import com.sampleapp.dialog.LoadingDialog
import com.sampleapp.base.BaseViewModel.Companion.DIALOG_LOADING
import com.vro.compose.VROComposableDialog
import com.vro.compose.VROComposableScreenContent
import com.vro.dialog.VRODialogState
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class BaseScreen<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> :
    VROComposableScreenContent<VM, S, D, E>() {

    @Composable
    override fun OnLoadDialog(data: VRODialogState) {
        when (data.type) {
            DIALOG_LOADING -> VROComposableDialog { LoadingDialog() }
        }
    }
}