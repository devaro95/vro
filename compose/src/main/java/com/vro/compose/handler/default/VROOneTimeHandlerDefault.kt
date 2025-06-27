package com.vro.compose.handler.default

import com.vro.compose.handler.VROOneTimeHandler
import com.vro.event.VROEvent
import com.vro.state.VROState

/**
 * Default implementation of [VROOneTimeHandler] that performs no action.
 *
 * This handler can be used when one-time effects or transient state updates
 * do not require any additional behavior.
 *
 * @param S The state type that extends [VROState]
 * @param E The event type that extends [VROEvent]
 */
class VROOneTimeHandlerDefault<S: VROState, E: VROEvent> : VROOneTimeHandler<S, E>() {

    /**
     * No-op implementation for handling one-time actions.
     *
     * This function intentionally does nothing. Use this as a placeholder when
     * no side-effect is needed for the given one-time `id` and `state`.
     *
     * @param id Identifier for the one-time event
     * @param state The current state at the time of the event
     */
    override fun onOneTime(id: Int, state: S) = Unit
}