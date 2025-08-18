package com.sampleapp.ui.template

import com.sampleapp.ui.template.SampleTemplateViewModel.Companion.FIRST_TEXT
import com.vro.state.VROState

data class SampleTemplateState(
    val sampleText: String
) : VROState {
    companion object {
        val INITIAL = SampleTemplateState(
            sampleText = FIRST_TEXT
        )
    }
}