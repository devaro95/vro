package com.sampleapp.ui.home.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.components.CardListItem
import com.sampleapp.components.SampleCardList
import com.sampleapp.topbar.sampleHomeToolbar
import com.sampleapp.ui.home.SampleHomeEvents
import com.sampleapp.ui.home.SampleHomeState
import com.vro.compose.composition.LocalAnimatedVisibilityScope
import com.vro.compose.composition.LocalSharedTransitionScope
import com.vro.compose.composition.LocalSnackbarState
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.screen.VROScreenContent
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarState
import com.vro.compose.states.VROTopBarBaseState
import com.vro.compose.utils.vroVerticalScroll
import com.vro.constants.INT_ZERO

class SampleHomeScreenContent : VROScreenContent<SampleHomeState, SampleHomeEvents>() {

    override fun setTopBar(currentState: VROTopBarBaseState, isScreenStarted: Boolean) = sampleHomeToolbar(context)

    override fun setBottomBar(currentState: VROBottomBarBaseState, isScreenStarted: Boolean) =
        VROBottomBarState(selectedItem = INT_ZERO)

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    override fun Content(state: SampleHomeState) {
        Column(
            modifier = Modifier
                .vroVerticalScroll()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val sharedScope = LocalSharedTransitionScope.current
            val animatedScope = LocalAnimatedVisibilityScope.current
            val snackBarState = LocalSnackbarState.current

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
                                state = snackBarState,
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
                    CardListItem(
                        text = "Template Management",
                        onClick = { event(SampleHomeEvents.Template) }
                    ),
                )
            )
            with(sharedScope) {
                Image(
                    painter = painterResource(R.drawable.header),
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState("this is the key"),
                            animatedVisibilityScope = animatedScope
                        )
                        .size(40.dp)
                        .clickable(onClick = { event(SampleHomeEvents.Profile) }),
                    contentDescription = null
                )
            }
        }
    }

    @Composable
    @VROLightMultiDevicePreview
    override fun ScreenPreview() {
        Content(state = SampleHomeState.INITIAL)
    }
}