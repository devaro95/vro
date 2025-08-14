package com.sampleapp.ui.samplefragment.destination.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.components.SampleButton
import com.sampleapp.topbar.sampleBackToolbar
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentEvents
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentState
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.screen.VROScreenContent
import com.vro.compose.states.VROTopBarBaseState

class SampleComposableDestinationFragmentScreenContent :
    VROScreenContent<SampleComposableDestinationFragmentState, SampleComposableDestinationFragmentEvents>() {

    override fun setTopBar(currentState: VROTopBarBaseState) = sampleBackToolbar(
        title = context.getString(R.string.destination_fragment_toolbar),
        onNavigation = { navigateBack() }
    )

    @Composable
    override fun Content(state: SampleComposableDestinationFragmentState) {
        Column(
            modifier = Modifier.Companion.fillMaxSize(),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            SampleButton(
                modifier=Modifier.padding(top = 32.dp),
                text = state.text,
                onClick = { event(SampleComposableDestinationFragmentEvents.Update) }
            )
            SampleButton(
                modifier=Modifier.padding(top = 16.dp),
                text = "Navigate Back Management",
                onClick = { navigateBack() }
            )
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun ScreenPreview() {
        Content(state = SampleComposableDestinationFragmentState.Companion.INITIAL)
    }
}