package com.vro.compose.handler.default

import androidx.compose.runtime.Composable
import com.vro.compose.handler.VRODialogHandler
import com.vro.event.VROEvent
import com.vro.state.VRODialogData

/**
 * Default implementation of [VRODialogHandler] that performs no dialog rendering.
 *
 * This can be used when no custom dialog UI is needed for certain [VROEvent]s.
 *
 * @param E The event type that extends [VROEvent]
 */
class VRODialogHandlerDefault<E : VROEvent> : VRODialogHandler<E>() {

    /**
     * No-op composable for dialog rendering.
     *
     * This function intentionally does nothing. It serves as a default handler
     * when no dialog should be shown.
     *
     * @param data Data used to build the dialog (ignored in this implementation)
     */
    @Composable
    override fun OnDialog(data: VRODialogData) = Unit
}