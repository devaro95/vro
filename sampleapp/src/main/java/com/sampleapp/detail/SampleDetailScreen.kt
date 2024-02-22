package com.sampleapp.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.vro.event.VROEvent

class SampleDetailScreen :
    SampleBaseScreen<SampleDetailState, SampleDestinations, SampleDetailEvent>() {

    @VROMultiDevicePreview
    @Composable
    override fun ComposablePreview() {
        GeneratePreview {
            ComposableContent(
                state = SampleDetailState.INITIAL,
                events = object : SampleDetailEvent {
                    override fun onActionProfileNavigationClick() = Unit
                })
        }
    }

    @Composable
    override fun ComposableContent(state: SampleDetailState, events: SampleDetailEvent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { events.onActionProfileNavigationClick() }
            ) {
                Text(text = "Update text")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { events.onActionProfileNavigationClick() }
            ) {
                Text(text = "Show Bottom Sheet")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { events.onActionProfileNavigationClick() }
            ) {
                Text(text = "Show Dialog Example")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { events.onActionProfileNavigationClick() }
            ) {
                Text(text = "Profile Navigation")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { events.onActionProfileNavigationClick() }
            ) {
                Text(text = "Detail Navigation")
            }
        }
    }

    override fun setTopBar(events: SampleDetailEvent) = sampleBackToolbar(
        title = context.getString(R.string.detail_toolbar),
        onNavigation = { navigateBack() }
    )
}