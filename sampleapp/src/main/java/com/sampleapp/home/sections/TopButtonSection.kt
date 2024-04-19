package com.sampleapp.home.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.home.SampleHomeEvents
import com.sampleapp.home.SampleHomeState
import com.vro.compose.VROComposableSection
import com.vro.compose.preview.VROLightMultiDevicePreview

class TopButtonSection : VROComposableSection<SampleHomeState, SampleHomeEvents>() {

    @Composable
    override fun CreateSection(state: SampleHomeState) {
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Top Button Section")
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { eventLauncher.onActionUpdateTextClick() }
            ) {
                Text(text = state.text)
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { eventLauncher.onActionShowBottomSheetClick() }
            ) {
                Text(text = "Show Bottom Sheet")
            }
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun CreatePreview() {
        CreateSection(SampleHomeState.INITIAL)
    }
}