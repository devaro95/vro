package com.vro.compose.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.state.VROState

abstract class VROComposableViewModelBottomSheetContent<S : VROState, E : VROEvent> : VROComposableDialogContentBasics<S, E>() {

    @Composable
    override fun CreateDialog(
        state: S,
        eventListener: VROEventListener<E>,
        listener: VRODialogListener?,
        onDismiss: () -> Unit,
    ) {
        super.CreateDialog(state, eventListener, listener, onDismiss)
        ComposableContent(state, onDismiss)
    }
}