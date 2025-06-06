package com.sampleapp.ui.detail.screen

import com.sampleapp.ui.base.SampleBaseScreen
import com.sampleapp.ui.detail.SampleDetailEvents
import com.sampleapp.ui.detail.SampleDetailState
import com.vro.compose.screen.VROScreenContent

class SampleDetailScreen(
    override val screenContent: VROScreenContent<SampleDetailState, SampleDetailEvents> = SampleDetailScreenContent(),
) : SampleBaseScreen<SampleDetailState, SampleDetailEvents>()