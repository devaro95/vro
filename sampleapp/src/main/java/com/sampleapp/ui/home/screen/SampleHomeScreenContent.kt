package com.sampleapp.ui.home.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.topbar.sampleBackToolbar
import com.sampleapp.ui.home.SampleHomeEvents
import com.sampleapp.ui.home.SampleHomeState
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.screen.VROScreenContent
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarState
import com.vro.compose.states.VROTopBarBaseState
import com.vro.compose.utils.vroVerticalScroll
import com.vro.constants.INT_ZERO

class SampleHomeScreenContent : VROScreenContent<SampleHomeState, SampleHomeEvents>() {

    override fun setTopBar(currentState: VROTopBarBaseState) = sampleBackToolbar(
        title = context.getString(R.string.home_toolbar),
        onNavigation = { navigateBack() }
    )

    override fun setBottomBar(currentState: VROBottomBarBaseState) =
        VROBottomBarState(selectedItem = INT_ZERO)

    @Composable
    override fun Content(state: SampleHomeState) {
        Column(
            modifier = Modifier
                .vroVerticalScroll()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.UpdateText) }
            ) {
                Text(text = state.text)
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowNavigationBottomSheet) }
            ) {
                Text(text = "Show Navigation Bottom Sheet")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowBottomSheet) }
            ) {
                Text(text = "Show Bottom Sheet")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowSimpleDialog) }
            ) {
                Text(text = "Show Dialog Example")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowSimpleDialogWithViewModel) }
            ) {
                Text(text = "Show Dialog VM Example")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = {
                    showSnackbar(
                        message = "Snackbar Example",
                        actionLabel = "action"
                    )
                }
            ) {
                Text(text = "Show Snackbar")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event((SampleHomeEvents.Profile)) }
            ) {
                Text(text = "Profile Navigation")
            }
            LastButtons()
        }
    }

    @Composable
    private fun LastButtons() {
        OutlinedButton(
            modifier = Modifier.padding(top = 16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
            onClick = { event(SampleHomeEvents.Detail) }
        ) {
            Text(text = "Detail Navigation")
        }
        OutlinedButton(
            modifier = Modifier.padding(top = 16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
            onClick = { event(SampleHomeEvents.ActivityFragment) }
        ) {
            Text(text = "Activity Fragment")
        }
        OutlinedButton(
            modifier = Modifier.padding(top = 16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
            onClick = { event(SampleHomeEvents.OneTimeLaunch) }
        ) {
            Text(text = "One Time Launch")
        }
    }

    @Composable
    @VROLightMultiDevicePreview
    override fun ScreenPreview() {
        Content(state = SampleHomeState.Companion.INITIAL)
    }
}