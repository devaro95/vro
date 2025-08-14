package com.sampleapp.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sampleapp.components.SampleButton
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.dialog.VRODialogListener

@VROLightMultiDevicePreview
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
                    .padding(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "VROSimple dialog",
                    fontWeight = FontWeight.Bold
                )
                SampleButton(
                    modifier = Modifier.padding(top = 50.dp),
                    text = "Hide Dialog",
                    onClick = data.onButtonClick
                )
            }
        }
    }
}

interface SampleSimpleDialogListener : VRODialogListener

data class SampleSimpleDialogData(
    val onButtonClick: () -> Unit,
)