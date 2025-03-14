package com.sampleapp.dialog.bottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sampleapp.dialog.SampleSimpleDialog
import com.sampleapp.dialog.SampleSimpleDialogData
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetNavViewModel.Companion.DIALOG
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.preview.VROMultiDevicePreview
import com.vro.state.VRODialogData

class SampleBottomSheet : VROComposableViewModelBottomSheetContent<SampleBottomSheetState, SampleBottomSheetEvents>() {

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
                onClick = { event(SampleBottomSheetEvents.OnDialog) }
            ) {
                Text(text = "Show Dialog")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleBottomSheetEvents.Dismiss) }
            ) {
                Text(text = "Dismiss")
            }
        }
    }

    @Composable
    override fun onDialog(data: VRODialogData) {
        when (data.type) {
            DIALOG -> SampleSimpleDialog(
                data = SampleSimpleDialogData(
                    onButtonClick = {}
                )
            )
        }
    }

    @VROMultiDevicePreview
    @Composable
    override fun ComposablePreview() {
        ComposableContent(SampleBottomSheetState.INITIAL) {}
    }
}