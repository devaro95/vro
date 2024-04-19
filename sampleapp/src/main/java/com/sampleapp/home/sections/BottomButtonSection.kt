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
import com.vro.compose.VROSection
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.utils.mobileModifier
import com.vro.compose.utils.tabletModifier

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
                onClick = { eventLauncher.onActionShowSimpleDialogClick() }
            ) {
                Text(text = "Show Dialog Example")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { eventLauncher.onActionProfileNavigationClick() }
            ) {
                Text(text = "Profile Navigation")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { eventLauncher.onActionDetailNavigationClick() }
            ) {
                Text(text = "Detail Navigation")
            }
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun CreatePreview() {
        CreateSection(SampleHomeState.INITIAL)
    }
}