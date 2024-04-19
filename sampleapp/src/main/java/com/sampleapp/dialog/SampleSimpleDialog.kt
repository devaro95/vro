package com.sampleapp.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vro.compose.preview.VROMultiDevicePreview
import com.vro.dialog.VRODialogListener

@VROMultiDevicePreview
@Composable
fun SampleSimpleComposableDialogPreview() {
    SampleSimpleDialog(SampleSimpleDialogData {})
}

@Composable
fun SampleSimpleDialog(
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
        Box(
            Modifier.clip(RoundedCornerShape(10.dp))
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Simple dialog")
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = data.onButtonClick,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Hide dialog",
                        color = Color.White
                    )
                }
            }
        }
    }
}

interface SampleSimpleDialogListener : VRODialogListener

data class SampleSimpleDialogData(
    val onButtonClick: () -> Unit,
)