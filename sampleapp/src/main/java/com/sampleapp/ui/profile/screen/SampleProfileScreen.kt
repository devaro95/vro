package com.sampleapp.ui.profile.screen

import com.sampleapp.ui.base.SampleBaseScreen
import com.sampleapp.ui.profile.SampleProfileEvents
import com.sampleapp.ui.profile.SampleProfileState
import com.vro.compose.screen.VROScreenContent

class SampleProfileScreen(
    override val screenContent: VROScreenContent<SampleProfileState, SampleProfileEvents> = SampleProfileScreenContent(),
) : SampleBaseScreen<SampleProfileState, SampleProfileEvents>()