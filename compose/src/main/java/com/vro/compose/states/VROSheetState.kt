/**
 * State management utilities for bottom sheet components.
 */
package com.vro.compose.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Creates and remembers a [SheetStateVro] instance that survives recompositions.
 *
 * @return A remembered instance of [SheetStateVro] for managing sheet visibility
 *
 * @see SheetStateVro for available state management functions
 */
@Composable
fun rememberSheetState(): SheetStateVro {
    return remember { SheetStateVro() }
}

/**
 * State holder for managing bottom sheet visibility and actions.
 * Provides functionality to programmatically hide the sheet and set callbacks.
 */
class SheetStateVro {
    private var hideActionListener: (() -> Unit)? = null

    /**
     * Sets the callback to be executed when the sheet should be hidden.
     *
     * @param action The callback function to invoke when hiding the sheet
     *
     * @note This is an internal API meant to be used by sheet composables
     *       to register their hide implementations
     */
    @Composable
    internal fun setHideActionListener(action: () -> Unit) {
        hideActionListener = action
    }

    /**
     * Triggers the sheet hide action if a listener has been registered.
     *
     * @see setHideActionListener for registering the hide implementation
     */
    fun hide() {
        hideActionListener?.invoke()
    }
}