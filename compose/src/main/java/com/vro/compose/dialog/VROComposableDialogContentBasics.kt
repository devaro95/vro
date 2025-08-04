package com.vro.compose.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.VROSkeleton
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.state.VRODialogData
import com.vro.state.VROState

/**
 * Abstract base class providing core functionality for Compose dialog implementations.
 * Serves as the foundation for all VRO dialog components with:
 * - State management
 * - Event handling
 * - Skeleton loading support
 * - Dialog lifecycle management
 * - Error handling
 *
 * @param S The state type extending [VROState] that this dialog will use
 * @param E The event type extending [VROEvent] that this dialog can emit
 *
 * ## Key Features
 * - Automatic event delegation
 * - Built-in loading states
 * - Dialog state management
 * - Preview support
 * - Error handling hooks
 *
 * ## Implementation Requirements
 * 1. Must implement [ComposableContent] for main dialog UI
 * 2. Must implement [ComposablePreview] for Android Studio previews
 * 3. Should override hooks like [onDialog] and [onError] as needed
 *
 * @see VROComposableDialogContent For complete dialog implementations
 */
abstract class VROComposableDialogContentBasics<S : VROState, E : VROEvent> {

    /**
     * Optional skeleton loader to show during loading states.
     * Set to provide custom loading placeholders that match your dialog's layout.
     */
    open val skeleton: VROSkeleton? = null

    /**
     * The Android context for the dialog.
     * Initialized automatically when dialog is shown.
     *
     * ## Usage
     * - Access resources
     * - Get system services
     * - Should only be accessed after dialog creation
     */
    lateinit var context: Context

    /**
     * Listener for dialog-specific events and callbacks.
     * Automatically set during dialog creation.
     */
    var dialogListener: VRODialogListener? = null

    /**
     * Event launcher for sending dialog events upstream.
     * Initialized automatically when dialog is shown.
     */
    lateinit var events: VROEventLauncher<E>

    /**
     * Base dialog creation function that initializes core dialog functionality.
     *
     * @param state The current state to render
     * @param events Launcher for dialog events
     * @param listener Callback handler for dialog actions
     * @param onDismiss Callback for dismissal requests
     *
     * @note Most implementations should override [ComposableContent] rather than this method.
     */
    @Composable
    internal open fun CreateDialog(
        state: S,
        events: VROEventLauncher<E>,
        listener: VRODialogListener?,
        onDismiss: () -> Unit,
    ) {
        this.events = events
        dialogListener = listener
    }

    /**
     * Default skeleton loading UI for the dialog.
     * Uses the provided [skeleton] implementation if available.
     */
    @Composable
    internal fun ComposableDialogSkeleton() {
        skeleton?.SkeletonContent()
    }

    /**
     * Abstract function defining the dialog's main content.
     * Must be implemented by all concrete dialog classes.
     *
     * @param state The current state to render
     * @param dismiss Callback to trigger dialog dismissal
     */
    @Composable
    abstract fun ComposableContent(state: S, dismiss: () -> Unit)

    /**
     * Abstract function providing a preview of the dialog.
     * Must be implemented with @[VROLightMultiDevicePreview] annotation.
     */
    @VROLightMultiDevicePreview
    @Composable
    abstract fun ComposablePreview()

    /**
     * Optional override for custom skeleton loading UI.
     * Default implementation shows nothing.
     */
    @Composable
    open fun ComposableSkeleton() = Unit

    /**
     * Handler for nested dialog requests.
     * Override to show child dialogs when requested.
     *
     * @param data Configuration for the requested dialog
     */
    @SuppressLint("ComposableNaming")
    @Composable
    open fun onDialog(data: VRODialogData) = Unit

    /**
     * Handler for error states.
     * Override to implement custom error presentation.
     *
     * @param error The exception that occurred
     * @param data Additional error context if available
     */
    @SuppressLint("ComposableNaming")
    @Composable
    open fun onError(error: Throwable, data: Any?) = Unit

    /**
     * Convenience method for sending events from the dialog.
     *
     * @param event The event to dispatch
     */
    fun event(event: E) {
        events.doEvent(event)
    }
}