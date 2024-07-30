package com.sampleapp.ui.samplefragment

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sampleapp.ui.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.screen.VROFragmentScreen

class SampleComposableFragmentScreen :
    VROFragmentScreen<SampleComposableFragmentState, SampleComposableFragmentDestinations, SampleComposableFragmentEvents>() {

    @Composable
    override fun ScreenContent(state: SampleComposableFragmentState) {
        Column {
            Text(text = "This is a Composable Fragment Screen")
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun ScreenPreview() {
        ScreenContent(state = SampleComposableFragmentState.INITIAL)
    }
}