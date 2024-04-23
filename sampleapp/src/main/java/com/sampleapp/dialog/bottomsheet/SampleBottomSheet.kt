package com.sampleapp.dialog.bottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetNavigator.SampleBottomSheetDestinations
import com.sampleapp.home.SampleHomeEvents
import com.vro.compose.dialog.VROComposableBottomSheetContent

class SampleBottomSheet : VROComposableBottomSheetContent<
        SampleBottomSheetState,
        SampleBottomSheetDestinations,
        SampleBottomSheetEvents>() {

    @Composable
    override fun ComposableContent(state: SampleBottomSheetState) {
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
        }
    }
}