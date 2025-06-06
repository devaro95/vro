package com.vro.compose.handler

import androidx.compose.runtime.Composable
import com.vro.event.VROEvent
import com.vro.state.VRODialogData

abstract class VRODialogHandler<E : VROEvent> : VROBaseHandler<E>() {

    @Composable
    abstract fun OnDialog(data: VRODialogData)
}