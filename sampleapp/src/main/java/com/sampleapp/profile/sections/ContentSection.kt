package com.sampleapp.profile.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.profile.SampleProfileEvent
import com.sampleapp.profile.SampleProfileState
import com.vro.compose.VROSection
import com.vro.compose.preview.VROLightMultiDevicePreview

class ContentSection : VROSection<SampleProfileState, SampleProfileEvent>() {

    @Composable
    override fun CreateSection(state: SampleProfileState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
        ) {
            Text(text = "This is the user profile")
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun CreatePreview() {
        CreateSection(SampleProfileState.INITIAL)
    }

}