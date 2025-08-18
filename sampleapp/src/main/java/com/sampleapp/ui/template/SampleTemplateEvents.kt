package com.sampleapp.ui.template

import com.vro.event.VROEvent

sealed class SampleTemplateEvents : VROEvent {
    data object ButtonClick : SampleTemplateEvents()
}