package com.sampleapp.dialog.bottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.preview.VROMultiDevicePreview

class SampleBottomSheet : VROComposableBottomSheetContent<SampleBottomSheetState, SampleBottomSheetEvents>() {

    @Composable
    override fun ComposableContent(state: SampleBottomSheetState, dismiss: () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = "This is a sample BottomSheet",
                color = Color.Black
            )
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleBottomSheetEvents.OnButton) }
            ) {
                Text(text = state.buttonText)
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = dismiss
            ) {
                Text(text = "Dismiss")
            }
        }
    }

    @VROMultiDevicePreview
    @Composable
    override fun ComposablePreview() {
        ComposableContent(SampleBottomSheetState.INITIAL, {})
    }
}