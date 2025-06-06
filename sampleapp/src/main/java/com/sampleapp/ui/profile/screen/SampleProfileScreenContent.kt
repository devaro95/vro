package com.sampleapp.ui.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.topbar.sampleBackToolbar
import com.sampleapp.ui.profile.SampleProfileEvents
import com.sampleapp.ui.profile.SampleProfileState
import com.vro.compose.screen.VROScreenContent
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROTopBarBaseState
import com.vro.constants.INT_ONE

class SampleProfileScreenContent: VROScreenContent<SampleProfileState, SampleProfileEvents>() {

    override fun setTopBar(currentState: VROTopBarBaseState) = sampleBackToolbar(
        title = context.getString(R.string.profile_toolbar),
        onNavigation = { navigateBack() }
    )

    override fun setBottomBar(currentState: VROBottomBarBaseState) =
        VROBottomBarBaseState.VROBottomBarState(selectedItem = INT_ONE)

    @Composable
    override fun Content(state: SampleProfileState) {
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
        ) {
            Text(text = "This is the user profile")
        }
    }

    @Composable
    override fun ScreenPreview() {
        Content(state = SampleProfileState.Companion.INITIAL)
    }
}