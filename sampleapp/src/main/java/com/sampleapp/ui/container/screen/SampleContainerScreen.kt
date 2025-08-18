package com.sampleapp.ui.container.screen

import com.sampleapp.ui.container.SampleContainerEvents
import com.sampleapp.ui.container.SampleContainerState
import com.sampleapp.ui.template.SampleTemplate
import com.sampleapp.ui.template.SampleTemplateDestinations
import com.sampleapp.ui.template.SampleTemplateEvents
import com.sampleapp.ui.template.mapper.SampleTemplateMapper
import com.sampleapp.ui.template.SampleTemplateRender
import com.sampleapp.ui.template.SampleTemplateState
import com.sampleapp.ui.template.SampleTemplateViewModel
import com.vro.compose.screen.VROScreen
import com.vro.compose.screen.VROScreenContent
import com.vro.compose.template.VROTemplate

class SampleContainerScreen(
    override val screenContent: VROScreenContent<SampleContainerState, SampleContainerEvents> = SampleContainerScreenContent(),
    override val screenTemplate: VROTemplate<SampleTemplateViewModel,
            SampleTemplateState,
            SampleTemplateDestinations,
            SampleTemplateEvents,
            SampleTemplateMapper,
            SampleTemplateRender>? = SampleTemplate()
) : VROScreen<SampleContainerState, SampleContainerEvents>()