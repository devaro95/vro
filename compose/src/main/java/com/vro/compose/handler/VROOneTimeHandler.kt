package com.vro.compose.handler

import com.vro.event.VROEvent
import com.vro.state.VROState

/**
 * Abstract handler for one-time events or actions that should be triggered only once
 * during a screen lifecycle or state transition.
 *
 * This class extends [VROBaseHandler]
 * Subclasses should implement [onOneTime] to react to single-occurrence events such as
 * navigation, toasts, or transient actions.
 *
 * @param S The state type that extends [VROState]
 * @param E The event type that extends [VROEvent]
 */
abstract class VROOneTimeHandler<S : VROState, E : VROEvent> : VROBaseHandler<E>() {

    /**
     * Called when a one-time action is triggered for the given [id] and current [state].
     *
     * This can be used for actions that must not repeat, such as showing a toast, navigating once,
     * or performing an animation.
     *
     * @param id A unique identifier for the one-time action
     * @param state The current state of the screen when the action is triggered
     */
    abstract fun onOneTime(id: Int, state: S)
}