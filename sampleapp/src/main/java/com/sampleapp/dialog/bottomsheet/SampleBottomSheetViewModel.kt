package com.sampleapp.dialog.bottomsheet

import com.sampleapp.dialog.bottomsheet.SampleBottomSheetEvents.OnButton
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetNavigator.SampleBottomSheetDestinations
import com.vro.compose.VROComposableViewModel
import com.vro.constants.EMPTY_STRING

class SampleBottomSheetViewModel :
    VROComposableViewModel<SampleBottomSheetState, SampleBottomSheetDestinations, SampleBottomSheetEvents>() {

    override val initialState = SampleBottomSheetState.INITIAL

    override fun eventListener(event: SampleBottomSheetEvents) {
        when (event) {
            is OnButton -> onNameChange()
        }
    }

    override suspend fun onStart() {
        updateScreen {
            copy(
                buttonText = FIRST_TEXT
            )
        }
    }

    private fun onNameChange() {
        checkDataState().also {
            updateScreen {
                copy(
                    buttonText = when (it.buttonText) {
                        FIRST_TEXT -> SECOND_TEXT
                        SECOND_TEXT -> FIRST_TEXT
                        else -> EMPTY_STRING
                    }
                )
            }
        }
    }

    companion object {
        private const val FIRST_TEXT = "Press to update"
        private const val SECOND_TEXT = "Press to update back"
    }

}