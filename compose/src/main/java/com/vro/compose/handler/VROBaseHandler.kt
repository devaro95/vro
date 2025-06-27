package com.vro.compose.handler

import android.content.Context
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher

/**
 * Base class for handling common behavior tied to screen-level event launching and context access.
 *
 * This class provides convenient access to the Android [Context] and an [events] launcher
 * for dispatching [VROEvent]s.
 *
 * Subclasses can use [event] to trigger UI logic or effects in response to user interaction
 * or state changes.
 *
 * @param E The event type that extends [VROEvent]
 */
open class VROBaseHandler<E : VROEvent> {

    /**
     * Event launcher used to dispatch events to the owning ViewModel.
     *
     * This property must be initialized before calling [event].
     */
    lateinit var events: VROEventLauncher<E>

    /**
     * The Android context for the screen. Must be initialized before use.
     *
     * Typically injected or set by the rendering environment.
     */
    lateinit var context: Context

    /**
     * Triggers the given [VROEvent] through the [events] launcher.
     *
     * This function delegates to [VROEventLauncher.doEvent] and can be used
     * by subclasses to emit UI or navigation events.
     *
     * @param event The event to dispatch
     */
    fun event(event: E) {
        events.doEvent(event)
    }
}