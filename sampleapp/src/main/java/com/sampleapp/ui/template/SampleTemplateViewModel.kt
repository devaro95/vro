package com.sampleapp.ui.template

import com.vro.constants.EMPTY_STRING
import com.vro.viewmodel.VROViewModel
import org.koin.core.annotation.Single

class SampleTemplateViewModel :
    VROViewModel<SampleTemplateState, SampleTemplateDestinations, SampleTemplateEvents>() {

    override val initialState: SampleTemplateState = SampleTemplateState.INITIAL

    override fun onEvent(event: SampleTemplateEvents) {
        when (event) {
            SampleTemplateEvents.ButtonClick -> onButtonClick()
        }
    }

    private fun onButtonClick() {
        checkDataState().also {
            updateScreen {
                copy(
                    sampleText = when (it.sampleText) {
                        FIRST_TEXT, EMPTY_STRING -> SECOND_TEXT
                        SECOND_TEXT -> FIRST_TEXT
                        else -> EMPTY_STRING
                    }
                )
            }
        }
    }

    companion object {
        const val FIRST_TEXT = "Press to update"
        const val SECOND_TEXT = "Press to update back"
    }
}