package com.sampleapp.ui.samplefragment.screen

import com.sampleapp.ui.samplefragment.SampleComposableFragmentEvents
import com.sampleapp.ui.samplefragment.SampleComposableFragmentState
import com.vro.compose.screen.VROScreen
import com.vro.compose.screen.VROScreenContent

class SampleComposableFragmentScreen(
    override val screenContent: VROScreenContent<SampleComposableFragmentState, SampleComposableFragmentEvents>
    = SampleComposableFragmentScreenContent(),
) : VROScreen<SampleComposableFragmentState, SampleComposableFragmentEvents>()