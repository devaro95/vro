package com.sampleapp.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.base.SampleBaseScreen
import com.sampleapp.main.SampleDestinations
import com.sampleapp.topbar.sampleBackToolbar
import com.vro.compose.extensions.GeneratePreview
import com.vro.compose.preview.VROMultiDevicePreview
import com.vro.event.VROEmptyEvent

class SampleProfileScreen :
    SampleBaseScreen<SampleProfileState, SampleDestinations, SampleProfileEvent>() {

    @VROMultiDevicePreview
    @Composable
    override fun ComposablePreview() {
        GeneratePreview {
            ComposableContent(SampleProfileState.INITIAL,
                events = object: SampleProfileEvent{

                })
        }
    }

    @Composable
    override fun ComposableContent(state: SampleProfileState, events: SampleProfileEvent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "This is the user profile")
        }
    }

    override fun setTopBar(events: SampleProfileEvent) = sampleBackToolbar(
        title = context.getString(R.string.profile_toolbar),
        onNavigation = { navigateBack() }
    )
}