package com.sampleapp.home

import com.sampleapp.base.SampleBaseViewModel
import com.sampleapp.main.SampleDestinations
import com.vro.constants.EMPTY_STRING
import java.io.Serializable

class SampleHomeViewModel : SampleBaseViewModel<SampleHomeState, SampleDestinations>(), SampleHomeEvent {

    override val initialState: SampleHomeState = SampleHomeState.INITIAL

    override fun onNavResult(result: Serializable) {
        super.onNavResult(result)
        if (result is Boolean) {
            if (result) updateScreen { copy(text = "esto es una prueba") }
        }
    }

    override suspend fun onStart() {
        updateScreen {
            copy(text = FIRST_TEXT)
        }
    }

    override fun onActionButtonClick() {
        checkDataState().also {
            updateScreen {
                copy(
                    text =
                    when (it.text) {
                        FIRST_TEXT -> SECOND_TEXT
                        SECOND_TEXT -> FIRST_TEXT
                        else -> EMPTY_STRING
                    }
                )
            }
        }
    }

    override fun navigateToProfile() {

    }

    override fun onActionHideDialog() {
        updateScreen()
    }

    companion object {
        const val FIRST_TEXT = "This is a text"
        const val SECOND_TEXT = "Another text"
    }
}