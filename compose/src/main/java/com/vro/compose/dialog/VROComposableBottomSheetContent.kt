package com.vro.compose.dialog

import android.content.Context
import androidx.compose.runtime.Composable
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.dialog.VRODialogListener
import com.vro.state.VROState

/**
 * Abstract base class for creating bottom sheet dialog content in Jetpack Compose.
 * Provides a structured way to implement state-driven bottom sheet dialogs with support for:
 * - State management
 * - Dialog event callbacks
 * - Multi-device previews
 * - Dismissal handling
 *
 * @param S The type of state this dialog will use, must extend [VROState]
 *
 * ## Basic Usage
 * 1. Create a subclass specifying your state type
 * 2. Implement [ComposableContent] with your dialog UI
 * 3. Implement [ComposablePreview] for Android Studio previews
 *
 * ## Example
 * ```
 * class MyBottomSheet : VROComposableBottomSheetContent<MyState>() {
 *     @Composable
 *     override fun ComposableContent(state: MyState, dismiss: () -> Unit) {
 *         // Your dialog content here
 *     }
 *
 *     @Composable
 *     override fun ComposablePreview() {
 *         ComposableContent(MyState(), {})
 *     }
 * }
 * ```
 *
 * @see VROComposableViewModelBottomSheetContent For ViewModel-backed variants
 */
abstract class VROComposableBottomSheetContent<S : VROState> {

    /**
     * The dialog listener for handling dialog-specific events.
     * Automatically set when the dialog is created via [CreateDialog].
     *
     * Use this to:
     * - Handle button clicks
     * - Notify parent components of dialog actions
     * - Trigger dismissals with results
     */
    var dialogListener: VRODialogListener? = null

    /**
     * The Android context for the dialog.
     *
     * ## Important
     * - Automatically set when used with VRO dialog components
     * - Must be accessed only after initialization
     * - Use for resources, system services, etc.
     */
    lateinit var context: Context

    /**
     * Internal dialog creation function that sets up the dialog environment.
     *
     * @param state The current state to render the dialog with
     * @param listener The dialog listener for callbacks
     * @param onDismiss Callback to trigger when dialog should dismiss
     *
     * @note This is automatically called by VRO dialog infrastructure.
     * Override [ComposableContent] instead for your UI implementation.
     */
    @Composable
    internal fun CreateDialog(
        state: S,
        listener: VRODialogListener?,
        onDismiss: () -> Unit,
    ) {
        dialogListener = listener
        ComposableContent(state, onDismiss)
    }

    /**
     * Abstract function defining the dialog's UI content.
     *
     * @param state The current state to render
     * @param dismiss Callback function to programmatically dismiss the dialog
     *
     * ## Implementation Notes
     * - Use the provided state to control your UI
     * - Call dismiss() when dismissal should occur (e.g., close button)
     * - Access [dialogListener] for event callbacks
     * - Use [context] for Android resources if needed
     */
    @Composable
    abstract fun ComposableContent(state: S, dismiss: () -> Unit)

    /**
     * Abstract function providing a preview of the dialog for Android Studio.
     *
     * ## Implementation Requirements
     * - Annotated with @[VROLightMultiDevicePreview]
     * - Should demonstrate typical dialog states
     * - Can use mock state data
     * - Should include all UI variants
     */
    @VROLightMultiDevicePreview
    @Composable
    abstract fun ComposablePreview()
}