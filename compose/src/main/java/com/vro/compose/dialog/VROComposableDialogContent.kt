package com.vro.compose.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.state.VROState

/**
 * Abstract base class for creating full-screen dialog content in Jetpack Compose.
 * Extends [VROComposableDialogContentBasics] to provide complete dialog functionality with:
 * - Back press handling
 * - Outside click dismissal
 * - State management
 * - Event handling
 * - Dialog properties configuration
 *
 * @param S The type of state this dialog will use, must extend [VROState]
 * @param E The type of events this dialog can emit, must extend [VROEvent]
 *
 * ## Basic Usage
 * 1. Create a subclass specifying your state and event types
 * 2. Implement [ComposableContent] with your dialog UI
 * 3. Configure dismissal behavior via properties
 * 4. Use with dialog showing mechanisms
 *
 * ## Example
 * ```kotlin
 * class MyDialog : VROComposableDialogContent<MyState, MyEvent>() {
 *     override val dismissOnBackPress = false // Prevent back button dismissal
 *
 *     @Composable
 *     override fun ComposableContent(state: MyState, dismiss: () -> Unit) {
 *         // Your dialog content here
 *     }
 * }
 * ```
 *
 * @see VROComposableDialogContentBasics For basic dialog functionality
 * @see Dialog For the underlying Compose dialog implementation
 */
abstract class VROComposableDialogContent<S : VROState, E : VROEvent> :
    VROComposableDialogContentBasics<S, E>() {

    /**
     * Controls whether the dialog can be dismissed by pressing the back button.
     *
     * Default: `true` (dialog will dismiss on back press)
     *
     * Set to `false` for:
     * - Mandatory dialogs
     * - Dialogs with their own back handling
     * - Action-confirmation dialogs
     */
    open val dismissOnBackPress: Boolean = true

    /**
     * Controls whether the dialog can be dismissed by clicking outside.
     *
     * Default: `true` (dialog will dismiss on outside click)
     *
     * Set to `false` for:
     * - Modal dialogs requiring explicit action
     * - Data entry dialogs
     * - Important notifications
     */
    open val dismissOnClickOutside: Boolean = true

    /**
     * Creates and shows the dialog with proper configuration.
     *
     * @param state The current state to render the dialog
     * @param events Launcher for dialog events
     * @param listener Callback handler for dialog actions
     * @param onDismiss Callback when dialog requests dismissal
     *
     * @note This implementation:
     * 1. Calls through to basic dialog setup
     * 2. Configures Compose [Dialog] with specified properties
     * 3. Delegates to [ComposableContent] for actual UI
     */
    @Composable
    override fun CreateDialog(
        state: S,
        events: VROEventLauncher<E>,
        listener: VRODialogListener?,
        onDismiss: () -> Unit,
    ) {
        super.CreateDialog(state, events, listener, onDismiss)
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside,
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            )
        ) {
            ComposableContent(state, onDismiss)
        }
    }
}