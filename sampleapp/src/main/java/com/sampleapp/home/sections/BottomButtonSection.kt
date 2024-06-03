package com.sampleapp.home.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.home.SampleHomeEvents
import com.sampleapp.home.SampleHomeState
import com.vro.compose.VROSection
import com.vro.compose.preview.VROLightMultiDevicePreview

class BottomButtonSection : VROSection<SampleHomeState, SampleHomeEvents>() {

    @Composable
    override fun CreateSection(state: SampleHomeState) {
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Bottom Button Section")
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowSimpleDialogClick) }
            ) {
                Text(text = "Show Dialog Example")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowSimpleDialogWithViewModelClick) }
            ) {
                Text(text = "Show Dialog VM Example")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ProfileNavigation) }
            ) {
                Text(text = "Profile Navigation")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.DetailNavigation) }
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
    override fun CreatePreview() {
        CreateSection(SampleHomeState.INITIAL)
    }
}