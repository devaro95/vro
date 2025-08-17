package com.sampleapp.ui.container.screen

import androidx.compose.runtime.Composable
import com.sampleapp.ui.container.SampleContainerEvents
import com.sampleapp.ui.container.SampleContainerState
import com.vro.compose.screen.VROScreenContent

class SampleContainerScreenContent :
    VROScreenContent<SampleContainerState, SampleContainerEvents>() {
    @Composable
    override fun Content(state: SampleContainerState) {

    }

    @Composable
    override fun ScreenPreview() {
        Content(SampleContainerState.INITIAL)
    }
}