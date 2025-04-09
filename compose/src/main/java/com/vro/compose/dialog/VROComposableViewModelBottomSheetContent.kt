package com.vro.compose.dialog

import androidx.compose.runtime.Composable
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.state.VROState

/**
 * Abstract base class for creating ViewModel-backed bottom sheet dialogs in Compose.
 * Extends [VROComposableDialogContentBasics] to provide a structured way to implement
 * bottom sheets that are driven by a ViewModel's state and events.
 *
 * @param S The state type extending [VROState] that this bottom sheet will use
 * @param E The event type extending [VROEvent] that this bottom sheet can emit
 *
 * ## Key Features
 * - ViewModel state management
 * - Built-in event handling
 * - Inherits all base dialog functionality
 * - Simplified bottom sheet implementation
 *
 * ## Implementation Requirements
 * 1. Extend this class with your specific state and event types
 * 2. Implement [ComposableContent] with your bottom sheet UI
 * 3. Implement [ComposablePreview] for Android Studio previews
 *
 * @see VROComposableDialogContentBasics For base functionality
 * @see VROComposableBottomSheetContent For non-ViewModel variants
 */
abstract class VROComposableViewModelBottomSheetContent<S : VROState, E : VROEvent> :
    VROComposableDialogContentBasics<S, E>() {

    /**
     * Creates and configures the bottom sheet dialog content.
     *
     * @param state The current state to render the bottom sheet
     * @param events Launcher for bottom sheet events
     * @param listener Callback handler for bottom sheet actions
     * @param onDismiss Callback when bottom sheet requests dismissal
     *
     * @note This implementation:
     * 1. Calls through to base dialog setup
     * 2. Delegates directly to [ComposableContent] for the UI
     * 3. Should typically not need to be overridden
     */
    @Composable
    override fun CreateDialog(
        state: S,
        events: VROEventLauncher<E>,
        listener: VRODialogListener?,
        onDismiss: () -> Unit,
    ) {
        super.CreateDialog(state, events, listener, onDismiss)
        ComposableContent(state, onDismiss)
    }
}