package com.vro.compose.handler.default

import androidx.compose.runtime.Composable
import com.vro.compose.handler.VRODialogHandler
import com.vro.event.VROEvent
import com.vro.state.VRODialogData

class VRODialogHandlerDefault<E : VROEvent> : VRODialogHandler<E>() {
    @Composable
    override fun OnDialog(data: VRODialogData) = Unit
}