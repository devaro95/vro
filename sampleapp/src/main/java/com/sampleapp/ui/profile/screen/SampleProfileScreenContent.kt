package com.sampleapp.ui.profile.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.topbar.sampleBackToolbar
import com.sampleapp.ui.home.SampleHomeEvents
import com.sampleapp.ui.profile.SampleProfileEvents
import com.sampleapp.ui.profile.SampleProfileState
import com.vro.compose.composition.LocalAnimatedVisibilityScope
import com.vro.compose.composition.LocalSharedTransitionScope
import com.vro.compose.screen.VROScreenContent
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROTopBarBaseState
import com.vro.constants.INT_ONE

class SampleProfileScreenContent : VROScreenContent<SampleProfileState, SampleProfileEvents>() {

    override fun setTopBar(currentState: VROTopBarBaseState, isScreenStarted: Boolean) = sampleBackToolbar(
        title = context.getString(R.string.profile_toolbar),
        onNavigation = { if(isScreenStarted) navigateBack() }
    )

    override fun setBottomBar(currentState: VROBottomBarBaseState, isScreenStarted: Boolean) =
        VROBottomBarBaseState.VROBottomBarState(selectedItem = INT_ONE)

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    override fun Content(state: SampleProfileState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
        ) {
            val sharedScope = LocalSharedTransitionScope.current
            val animatedScope = LocalAnimatedVisibilityScope.current
            with(sharedScope) {
                Image(
                    painter = painterResource(R.drawable.header),
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState("this is the key"),
                            animatedVisibilityScope = animatedScope
                        ),
                    contentDescription = null
                )
            }

            Text(text = "This is the user profile")
        }
    }

    @Composable
    override fun ScreenPreview() {
        Content(state = SampleProfileState.INITIAL)
    }
}