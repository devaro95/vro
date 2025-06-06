package com.sampleapp.ui.base

import com.vro.compose.screen.VROScreen
import com.vro.compose.handler.VRODialogHandler
import com.vro.event.VROEvent
import com.vro.state.VROState

abstract class SampleBaseScreen<S : VROState, E : VROEvent>(
    override val dialogHandler: VRODialogHandler<E> = SampleBaseDialogHandler(),
) : VROScreen<S, E>()