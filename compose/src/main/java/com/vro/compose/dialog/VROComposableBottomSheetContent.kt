package com.vro.compose.dialog

import android.content.Context
import androidx.compose.runtime.Composable
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.dialog.VRODialogListener
import com.vro.state.VROState

abstract class VROComposableBottomSheetContent<S : VROState> {

    var dialogListener: VRODialogListener? = null

    lateinit var context: Context

    @Composable
    internal fun CreateDialog(
        state: S,
        listener: VRODialogListener?,
        onDismiss: () -> Unit,
    ) {
        dialogListener = listener
        ComposableContent(state, onDismiss)
    }

    @Composable
    abstract fun ComposableContent(state: S, dismiss: () -> Unit)

    @VROLightMultiDevicePreview
    @Composable
    abstract fun ComposablePreview()
}