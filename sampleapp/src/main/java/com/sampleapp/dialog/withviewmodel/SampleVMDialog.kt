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
import androidx.compose.ui.unit.dp
import com.sampleapp.dialog.withviewmodel.SampleVMDialogEvents.TextChange
import com.vro.compose.dialog.VROComposableDialogContent

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
                Text("Simple dialog")
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = dismiss,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Hide dialog",
                        color = Color.White
                    )
                }
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = { event(TextChange) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = state.buttonText,
                        color = Color.White
                    )
                }
            }
        }
    }
}