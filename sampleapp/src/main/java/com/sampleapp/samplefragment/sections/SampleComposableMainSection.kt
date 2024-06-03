package com.sampleapp.samplefragment.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sampleapp.samplefragment.SampleComposableFragmentEvents
import com.sampleapp.samplefragment.SampleComposableFragmentState
import com.vro.compose.VROSection
import com.vro.compose.preview.VROLightMultiDevicePreview

class SampleComposableMainSection : VROSection<SampleComposableFragmentState, SampleComposableFragmentEvents>() {



    @Composable
    override fun CreateSection(state: SampleComposableFragmentState) {
        Column {
            Text(text = "This is a Composable Fragment Screen")
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun CreatePreview() {
        CreateSection(SampleComposableFragmentState.INITIAL)
    }
}