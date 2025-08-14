package com.sampleapp.ui.home.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sampleapp.components.CardListItem
import com.sampleapp.components.SampleCardList
import com.sampleapp.topbar.sampleHomeToolbar
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

    override fun setTopBar(currentState: VROTopBarBaseState) = sampleHomeToolbar(context)

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
            SampleCardList(
                itemList = listOf(
                    CardListItem(
                        text = "State Management\n\n${state.text}",
                        onClick = { event(SampleHomeEvents.UpdateText) }
                    ),
                    CardListItem(
                        text = "Dialog State Management",
                        onClick = { event(SampleHomeEvents.ShowSimpleDialog) }
                    ),
                    CardListItem(
                        text = "Dialog With ViewModel Management",
                        onClick = { event(SampleHomeEvents.ShowSimpleDialogWithViewModel) }
                    ),
                    CardListItem(
                        text = "BottomSheet Management",
                        onClick = { event(SampleHomeEvents.ShowBottomSheet) }
                    ),
                    CardListItem(
                        text = "Navigation BottomSheet Management",
                        onClick = { event(SampleHomeEvents.ShowNavigationBottomSheet) }
                    ),
                    CardListItem(
                        text = "Snackbar Management",
                        onClick = {
                            showSnackbar(
                                message = "Hi mate!",
                                actionLabel = "Press Me"
                            )
                        }
                    ),
                    CardListItem(
                        text = "Navigation Profile Management",
                        onClick = { event((SampleHomeEvents.Profile)) }
                    ),
                    CardListItem(
                        text = "Params Navigation Management",
                        onClick = { event((SampleHomeEvents.Detail)) }
                    ),
                    CardListItem(
                        text = "Activity Fragment Management",
                        onClick = { event(SampleHomeEvents.ActivityFragment) }
                    ),
                    CardListItem(
                        text = "One Time Management",
                        onClick = { event(SampleHomeEvents.OneTimeLaunch) }
                    ),
                )
            )
        }
    }

    @Composable
    @VROLightMultiDevicePreview
    override fun ScreenPreview() {
        Content(state = SampleHomeState.Companion.INITIAL)
    }
}