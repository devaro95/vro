package com.vro.compose.dialog

import androidx.compose.runtime.Composable
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.state.VROState

abstract class VROComposableViewModelBottomSheetContent<S : VROState, E : VROEvent> : VROComposableDialogContentBasics<S, E>() {

    @Composable
    override fun CreateDialog(
        state: S,
        events: VROEventLauncher<E>,
        listener: VRODialogListener?,
        onDismiss: () -> Unit,
    ) {
        super.CreateDialog(state, events, listener, onDismiss)
        ComposableContent(state, onDismiss)
    }
}