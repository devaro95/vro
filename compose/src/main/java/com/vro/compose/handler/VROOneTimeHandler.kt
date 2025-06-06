package com.vro.compose.handler

import com.vro.event.VROEvent
import com.vro.state.VROState

abstract class VROOneTimeHandler<S : VROState, E : VROEvent> : VROBaseHandler<E>() {

    abstract fun onOneTime(id: Int, state: S)
}