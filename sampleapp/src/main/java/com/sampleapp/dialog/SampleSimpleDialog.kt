package com.sampleapp.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vro.compose.extensions.GeneratePreview
import com.vro.compose.preview.VROMultiDevicePreview
import com.vro.dialog.VRODialogListener

@VROMultiDevicePreview
@Composable
fun SimpleComposableDialogPreview() {
    GeneratePreview {
        SimpleDialog(SampleSimpleDialogData {})
    }
}

@Composable
fun SimpleDialog(
    data: SampleSimpleDialogData,
    listener: SampleSimpleDialogListener? = null,
) {
    Dialog(
        onDismissRequest = { listener?.onBackPressed() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column {
            Text("Simple dialog")
            Button(onClick = data.onButtonClick) {
                Text("Boton")
            }
        }
    }
}

interface SampleSimpleDialogListener : VRODialogListener

data class SampleSimpleDialogData(
    val onButtonClick: () -> Unit,
)