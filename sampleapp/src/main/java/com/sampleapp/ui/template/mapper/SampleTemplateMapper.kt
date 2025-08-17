package com.sampleapp.ui.template.mapper

import com.vro.compose.template.VROTemplateMapper

interface SampleTemplateMapper : VROTemplateMapper {
    fun getSampleTemplateTitle(): String
    fun getSampleTemplateText(): String
}