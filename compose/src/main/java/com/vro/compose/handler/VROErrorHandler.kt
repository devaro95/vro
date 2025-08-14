package com.vro.compose.handler

import androidx.compose.runtime.Composable
import com.vro.event.VROEvent

/**
 * Abstract handler for error-related logic within a Composable screen.
 *
 * This class extends [VROBaseHandler]
 * Subclasses should implement [OnError] to render or handle error-related UI based on the
 * received [Throwable] and optional data.
 *
 * This allows flexible error presentation (e.g., snackbars, dialogs) tailored to the UI context.
 *
 * @param E The event type that extends [VROEvent]
 */
abstract class VROErrorHandler<E : VROEvent> : VROBaseHandler<E>() {

    /**
     * Called to handle or render UI when an error occurs.
     *
     * This function should contain the Composable UI logic for showing the error,
     * such as displaying an error dialog or a toast/snackbar.
     *
     * @param error The exception or error that occurred
     * @param data Optional data associated with the error (e.g., failed payload)
     */
    @Composable
    abstract fun OnError(error: Throwable, data: Any?)
}