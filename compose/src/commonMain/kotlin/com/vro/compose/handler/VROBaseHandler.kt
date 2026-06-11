package com.vro.compose.handler

import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher

/**
 * Base class for handling common behavior tied to screen-level event launching.
 *
 * @param E The event type that extends [VROEvent]
 */
open class VROBaseHandler<E : VROEvent> {

    lateinit var events: VROEventLauncher<E>

    fun event(event: E) {
        events.doEvent(event)
    }
}
