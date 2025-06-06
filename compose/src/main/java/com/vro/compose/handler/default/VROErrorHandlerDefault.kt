package com.vro.compose.handler.default

import androidx.compose.runtime.Composable
import com.vro.compose.handler.VROErrorHandler
import com.vro.event.VROEvent

class VROErrorHandlerDefault<E : VROEvent> : VROErrorHandler<E>() {
    @Composable
    override fun OnError(error: Throwable, data: Any?) = Unit
}