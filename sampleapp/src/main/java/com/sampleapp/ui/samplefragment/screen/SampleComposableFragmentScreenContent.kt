package com.sampleapp.ui.samplefragment.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.components.SampleButton
import com.sampleapp.topbar.sampleBackToolbar
import com.sampleapp.ui.samplefragment.SampleComposableFragmentEvents
import com.sampleapp.ui.samplefragment.SampleComposableFragmentState
import com.vro.compose.screen.VROScreenContent
import com.vro.compose.states.VROTopBarBaseState

class SampleComposableFragmentScreenContent :
    VROScreenContent<SampleComposableFragmentState, SampleComposableFragmentEvents>() {

    override fun setTopBar(currentState: VROTopBarBaseState, isScreenStarted: Boolean) = sampleBackToolbar(
        title = context.getString(R.string.composable_fragment),
        onNavigation = { navigateBack() }
    )

    @Composable
    override fun Content(state: SampleComposableFragmentState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SampleButton(
                modifier = Modifier.padding(top = 16.dp),
                text = state.text,
                onClick = { event(SampleComposableFragmentEvents.Update) }
            )
            SampleButton(
                modifier = Modifier.padding(top = 16.dp),
                text = "Navigate Back Management",
                onClick = { navigateBack() }
            )
            SampleButton(
                modifier = Modifier.padding(top = 16.dp),
                text = "Composable Navigation Management",
                onClick = { event(SampleComposableFragmentEvents.NavigateDestination) }
            )
        }
    }

    @Composable
    override fun ScreenPreview() {
        Content(state = SampleComposableFragmentState.INITIAL)
    }
}