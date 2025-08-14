package com.sampleapp.dialog.withviewmodel

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
import com.sampleapp.components.SampleButton
import com.sampleapp.dialog.withviewmodel.SampleVMDialogEvents.TextChange
import com.vro.compose.dialog.VROComposableDialogContent
import com.vro.compose.preview.VROLightMultiDevicePreview

class SampleVMDialog : VROComposableDialogContent<SampleVMDialogState, SampleVMDialogEvents>() {
    @Composable
    override fun ComposableContent(
        state: SampleVMDialogState,
        dismiss: () -> Unit,
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
                Text(
                    text = "VRO ViewModel Dialog",
                    fontWeight = FontWeight.Bold
                )
                SampleButton(
                    modifier = Modifier.padding(top = 50.dp),
                    text = state.buttonText,
                    onClick = { event(TextChange) }
                )
                SampleButton(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Hide Dialog",
                    onClick = { (dialogListener as? SampleVMDialogListener)?.hideDialog() }
                )
            }
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun ComposablePreview() {
        ComposableContent(state = SampleVMDialogState.INITIAL, dismiss = {})
    }
}