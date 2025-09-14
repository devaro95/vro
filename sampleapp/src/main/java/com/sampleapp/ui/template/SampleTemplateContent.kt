package com.sampleapp.ui.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.ui.template.mapper.SampleTemplateMapper
import com.vro.compose.di.injectMapper
import com.vro.compose.template.VROTemplateContent

class SampleTemplateContent : VROTemplateContent<SampleTemplateState, SampleTemplateEvents, SampleTemplateMapper, SampleTemplateRender>() {

    override val mapper: SampleTemplateMapper by injectMapper()

    override fun render(state: SampleTemplateState): SampleTemplateRender = SampleTemplateRender(
        state = state,
        mapper = mapper,
        events = events
    )

    @Composable
    override fun Content(state: SampleTemplateState) {
        render(state).apply {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp)
            ) {
                GetTemplateTitle()
                GetTemplateButtonSection()
            }
        }
    }

    @Composable
    override fun ScreenPreview() {

    }
}