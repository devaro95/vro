package com.vro.compose.handler.default

import androidx.compose.runtime.Composable
import com.vro.compose.handler.VROErrorHandler
import com.vro.event.VROEvent

/**
 * Default implementation of [VROErrorHandler] that performs no error rendering.
 *
 * This can be used when no specific UI feedback is required for handled [VROEvent] errors.
 *
 * @param E The event type that extends [VROEvent]
 */
class VROErrorHandlerDefault<E : VROEvent> : VROErrorHandler<E>() {

    /**
     * No-op composable for error handling.
     *
     * This function intentionally does nothing. It serves as a default handler
     * when errors should be silently ignored or logged elsewhere.
     *
     * @param error The thrown error or exception (ignored in this implementation)
     * @param data Optional extra data related to the error (ignored in this implementation)
     */
    @Composable
    override fun OnError(error: Throwable, data: Any?) = Unit
}