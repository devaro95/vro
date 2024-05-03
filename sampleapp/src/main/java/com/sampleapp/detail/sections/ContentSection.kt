package com.sampleapp.detail.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.detail.SampleDetailEvent
import com.sampleapp.detail.SampleDetailState
import com.sampleapp.main.SampleDestinations
import com.vro.compose.VROSection
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.event.VROEvent

class ContentSection : VROSection<SampleDetailState, SampleDetailEvent>() {

    @Composable
    override fun CreateSection(state: SampleDetailState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
        ) {
            Text(text = "This is the detail screen")
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun CreatePreview() {
        CreateSection(SampleDetailState.INITIAL)
    }
}