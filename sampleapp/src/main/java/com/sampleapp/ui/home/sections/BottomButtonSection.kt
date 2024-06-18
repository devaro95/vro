package com.sampleapp.ui.home.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.ui.home.SampleHomeEvents
import com.sampleapp.ui.home.SampleHomeState
import com.vro.compose.VROSection
import com.vro.compose.preview.VROLightMultiDevicePreview

class BottomButtonSection : VROSection<SampleHomeState, SampleHomeEvents>() {

    @Composable
    override fun SectionContent(state: SampleHomeState) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Bottom Button Section")
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowSimpleDialog) }
            ) {
                Text(text = "Show Dialog Example")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowSimpleDialogWithViewModel) }
            ) {
                Text(text = "Show Dialog VM Example")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.Profile) }
            ) {
                Text(text = "Profile Navigation")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.Detail) }
            ) {
                Text(text = "Detail Navigation")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ActivityFragment) }
            ) {
                Text(text = "Activity Fragment")
            }
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun SectionPreview() {
        SectionContent(SampleHomeState.INITIAL)
    }
}