package com.vro.compose.handler

import androidx.compose.runtime.Composable
import com.vro.event.VROEvent
import com.vro.state.VRODialogData

/**
 * Abstract handler for dialog-related logic within a Composable screen.
 *
 * This class extends [VROBaseHandler] to inherit access to [Context] and event dispatching.
 * Subclasses should implement [OnDialog] to render dialog UI based on the provided [VRODialogData].
 *
 * @param E The event type that extends [VROEvent]
 */
abstract class VRODialogHandler<E : VROEvent> : VROBaseHandler<E>() {

    /**
     * Called to render the dialog content based on the provided [data].
     *
     * This function should contain the Composable UI logic for showing a dialog,
     * such as alerts, confirmations, or custom modal content.
     *
     * @param data The dialog data used to render the appropriate UI
     */
    @Composable
    abstract fun OnDialog(data: VRODialogData)
}