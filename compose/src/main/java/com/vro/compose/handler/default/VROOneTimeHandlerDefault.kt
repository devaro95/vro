package com.vro.compose.handler.default

import com.vro.compose.handler.VROOneTimeHandler
import com.vro.event.VROEvent
import com.vro.state.VROState

class VROOneTimeHandlerDefault<S: VROState, E: VROEvent> : VROOneTimeHandler<S, E>() {
    override fun onOneTime(id: Int, state: S) = Unit
}