package com.vro.compose.template

import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.navigation.VROBackResult
import com.vro.state.VROState
import org.koin.core.component.KoinComponent

/**
 * Interface representing the rendering logic for a [VROTemplate] screen.
 *
 * This interface encapsulates both the current UI [state] and the [events] launcher,
 * and provides convenience methods to dispatch events or handle back navigation.
 *
 * It is meant to be implemented by classes responsible for rendering the UI
 * based on the current state, while being able to trigger actions or navigate.
 *
 * @param E The type of events that extend [VROEvent].
 * @param S The type of UI state that extends [VROState].
 */
interface VROTemplateRender<E : VROEvent, S : VROState> : KoinComponent {

    /** The current UI state to be rendered. */
    val state: S

    /** Event dispatcher to trigger [VROEvent] actions. */
    val events: VROEventLauncher<E>

    /**
     * Dispatches an event to the ViewModel.
     *
     * @param event The event to dispatch
     */
    fun event(event: E) {
        events.doEvent(event)
    }

    /**
     * Triggers a back navigation action, optionally passing a result.
     *
     * @param result An optional [VROBackResult] to be sent back to the previous screen.
     */
    fun doBack(result: VROBackResult? = null) {
        events.doBack(result)
    }
}
