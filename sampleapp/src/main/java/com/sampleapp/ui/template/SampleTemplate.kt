package com.sampleapp.ui.template

import com.sampleapp.ui.template.mapper.SampleTemplateMapper
import com.vro.compose.template.VROTemplate
import com.vro.compose.template.VROTemplateContent

class SampleTemplate(
    override val templateContent: VROTemplateContent<SampleTemplateState, SampleTemplateEvents, SampleTemplateMapper, SampleTemplateRender> =
        SampleTemplateContent()
) : VROTemplate<SampleTemplateState, SampleTemplateEvents, SampleTemplateMapper, SampleTemplateRender>()