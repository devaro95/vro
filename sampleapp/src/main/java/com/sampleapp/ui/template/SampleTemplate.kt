package com.sampleapp.ui.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.ui.template.mapper.SampleTemplateMapper
import com.vro.compose.di.injectMapper
import com.vro.compose.di.injectNavigator
import com.vro.compose.di.injectViewModel
import com.vro.compose.navigator.VROTemplateNav
import com.vro.compose.template.VROTemplate

class SampleTemplate :
    VROTemplate<SampleTemplateViewModel,
            SampleTemplateState,
            SampleTemplateDestinations,
            SampleTemplateEvents,
            SampleTemplateMapper, SampleTemplateRender>() {

    override val viewModel: SampleTemplateViewModel by injectViewModel()

    override val navigator: VROTemplateNav<SampleTemplateDestinations> by injectNavigator()

    override val mapper: SampleTemplateMapper by injectMapper()

    @Composable
    override fun render(state: SampleTemplateState): SampleTemplateRender {
        return SampleTemplateRender(state = state, events = events, mapper = mapper)
    }

    @Composable
    override fun TemplateContent(state: SampleTemplateState) {
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
    override fun TemplatePreview() {
        TemplateContent(SampleTemplateState.INITIAL)
    }
}