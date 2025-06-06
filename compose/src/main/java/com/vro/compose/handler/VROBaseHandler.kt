package com.vro.compose.handler

import android.content.Context
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher

open class VROBaseHandler<E : VROEvent> {

    lateinit var events: VROEventLauncher<E>

    /**
     * The Android context for the screen. Must be initialized before use.
     */
    lateinit var context: Context

    fun event(event: E) {
        events.doEvent(event)
    }
}