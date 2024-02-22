package com.sampleapp.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sampleapp.base.SampleBaseScreen
import com.sampleapp.components.SampleButtonSkeleton
import com.sampleapp.components.SampleTextSkeleton
import com.sampleapp.detail.SampleDetailEvent
import com.sampleapp.main.SampleDestinations
import com.sampleapp.topbar.sampleHomeToolbar
import com.vro.compose.extensions.GeneratePreview
import com.vro.compose.preview.VROMultiDevicePreview
import com.vro.event.VROEvent

class SampleHomeScreen : SampleBaseScreen<SampleHomeState, SampleDestinations, SampleHomeEvents>() {

    @Composable
    @VROMultiDevicePreview
    override fun ComposablePreview() {
        GeneratePreview {
            ComposableContent(SampleHomeState.INITIAL,
                events = object : SampleHomeEvents {
                    override fun navigateToProfile() = Unit
                    override fun onActionHideDialog() = Unit
                    override fun onActionUpdateTextClick() = Unit
                    override fun onActionShowBottomSheetClick() = Unit
                    override fun onActionShowSimpleDialogClick() = Unit
                    override fun onActionBottomSheetDismiss() = Unit
                    override fun onActionProfileNavigationClick() = Unit
                    override fun onActionDetailNavigationClick() = Unit
                })
        }
    }

    @Composable
    override fun ComposableContent(state: SampleHomeState, events: SampleHomeEvents) {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.text,
                textAlign = TextAlign.Center
            )
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { events.onActionUpdateTextClick() }
            ) {
                Text(text = "Update text")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { events.onActionShowBottomSheetClick() }
            ) {
                Text(text = "Show Bottom Sheet")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { events.onActionShowSimpleDialogClick() }
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
                onClick = { events.onActionDetailNavigationClick() }
            ) {
                Text(text = "Detail Navigation")
            }
        }
    }

    override fun setTopBar(events: SampleHomeEvents) =
        context.sampleHomeToolbar(
            onAction = { events.navigateToProfile() }
        )
}