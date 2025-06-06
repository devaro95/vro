package com.sampleapp.ui.samplefragment.destination.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sampleapp.R
import com.sampleapp.topbar.sampleBackToolbar
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentEvents
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentState
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.screen.VROScreenContent
import com.vro.compose.states.VROTopBarBaseState

class SampleComposableDestinationFragmentScreenContent :
    VROScreenContent<SampleComposableDestinationFragmentState, SampleComposableDestinationFragmentEvents>() {

    override fun setTopBar(currentState: VROTopBarBaseState) = sampleBackToolbar(
        title = context.getString(R.string.profile_toolbar),
        onNavigation = { navigateBack() }
    )

    @Composable
    override fun Content(state: SampleComposableDestinationFragmentState) {
        Column(
            modifier = Modifier.Companion.fillMaxSize(),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.Companion.padding(top = 16.dp),
                text = state.text,
                fontSize = 24.sp
            )
            OutlinedButton(
                modifier = Modifier.Companion.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleComposableDestinationFragmentEvents.BackWithResult) }
            ) {
                Text(text = "Navigate Back with result")
            }
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun ScreenPreview() {
        Content(state = SampleComposableDestinationFragmentState.Companion.INITIAL)
    }
}