package com.vro.compose.handler

import androidx.compose.runtime.Composable
import com.vro.event.VROEvent

abstract class VROErrorHandler<E : VROEvent> : VROBaseHandler<E>() {

    @Composable
    abstract fun OnError(error: Throwable, data: Any?)
}