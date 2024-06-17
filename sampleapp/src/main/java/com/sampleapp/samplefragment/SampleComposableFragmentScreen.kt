package com.sampleapp.samplefragment

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations
import com.sampleapp.samplefragment.sections.SampleComposableMainSection
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.screen.VROFragmentScreen

class SampleComposableFragmentScreen :
    VROFragmentScreen<SampleComposableFragmentState, SampleComposableFragmentDestinations, SampleComposableFragmentEvents>() {

    @Composable
    override fun Modifier.setModifier() = this.padding(32.dp)

    @Composable
    override fun screenSections() = listOf(SampleComposableMainSection())

    @VROLightMultiDevicePreview
    @Composable
    override fun ScreenPreview() {
        CreatePreview()
    }
}