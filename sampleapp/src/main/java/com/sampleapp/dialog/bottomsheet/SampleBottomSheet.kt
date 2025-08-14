package com.sampleapp.dialog.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sampleapp.components.SampleButton
import com.sampleapp.dialog.SampleSimpleDialog
import com.sampleapp.dialog.SampleSimpleDialogData
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetNavViewModel.Companion.DIALOG
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.preview.VROMultiDevicePreview
import com.vro.state.VRODialogData

class SampleBottomSheet :
    VROComposableViewModelBottomSheetContent<SampleBottomSheetState, SampleBottomSheetEvents>() {

    @Composable
    override fun ComposableContent(state: SampleBottomSheetState, dismiss: () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = "VRO BottomSheet",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            SampleButton(
                modifier = Modifier.padding(top = 32.dp),
                text = state.buttonText,
                onClick = { event(SampleBottomSheetEvents.OnButton) }
            )
            SampleButton(
                modifier = Modifier.padding(top = 16.dp),
                text = "Dialog Management",
                onClick = { event(SampleBottomSheetEvents.OnDialog) }
            )
            SampleButton(
                modifier = Modifier.padding(top = 16.dp),
                text = "Hide dialog",
                onClick = { event(SampleBottomSheetEvents.Dismiss) }
            )
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