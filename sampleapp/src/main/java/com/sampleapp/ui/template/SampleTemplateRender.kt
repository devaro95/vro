package com.sampleapp.ui.template

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sampleapp.components.SampleButton
import com.sampleapp.ui.template.mapper.SampleTemplateMapper
import com.vro.compose.template.VROTemplateRender
import com.vro.event.VROEventLauncher

class SampleTemplateRender(
    override val state: SampleTemplateState,
    override val events: VROEventLauncher<SampleTemplateEvents>,
    val mapper: SampleTemplateMapper
) : VROTemplateRender<SampleTemplateEvents, SampleTemplateState> {

    @Composable
    fun GetTemplateTitle() {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            text = mapper.getSampleTemplateTitle(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    fun GetTemplateButtonSection() {
        SampleButton(
            modifier = Modifier.padding(top = 16.dp),
            text = state.sampleText,
            onClick = { event(SampleTemplateEvents.ButtonClick) }
        )
    }
}