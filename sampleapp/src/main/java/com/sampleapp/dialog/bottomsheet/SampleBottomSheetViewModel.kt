package com.sampleapp.dialog.bottomsheet

import com.sampleapp.dialog.bottomsheet.SampleBottomSheetEvents.OnButton
import com.vro.constants.EMPTY_STRING
import com.vro.core_android.viewmodel.VRODialogViewModel

class SampleBottomSheetViewModel :
    VRODialogViewModel<SampleBottomSheetState, SampleBottomSheetEvents>() {

    override val initialState = SampleBottomSheetState.INITIAL

    override fun eventListener(event: SampleBottomSheetEvents) {
        when (event) {
            is OnButton -> onNameChange()
        }
    }

    override fun onStart() {
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
                        FIRST_TEXT, EMPTY_STRING -> SECOND_TEXT
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