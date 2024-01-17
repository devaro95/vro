package com.vro.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun VROComposableDialog(
    isCancelable: Boolean = true,
    onDismiss: (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
) {
    var dismiss by remember { mutableStateOf(false) }
    if (dismiss) return

    Dialog(
        onDismissRequest = {
            dismiss = true
            onDismiss?.invoke()
        },
        properties = DialogProperties(
            dismissOnBackPress = isCancelable,
            dismissOnClickOutside = isCancelable
        )
    ) {
        content?.invoke()
    }
}