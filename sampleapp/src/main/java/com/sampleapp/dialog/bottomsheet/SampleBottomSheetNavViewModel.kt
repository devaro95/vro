package com.sampleapp.dialog.bottomsheet

import com.sampleapp.dialog.bottomsheet.SampleBottomSheetEvents.Dismiss
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetEvents.OnButton
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetEvents.OnDialog
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetNavigator.SampleBottomSheetDestinations
import com.vro.constants.EMPTY_STRING
import com.vro.core_android.viewmodel.VROViewModel
import com.vro.state.VRODialogData

class SampleBottomSheetNavViewModel :
    VROViewModel<SampleBottomSheetState, SampleBottomSheetDestinations, SampleBottomSheetEvents>() {

    override val initialState = SampleBottomSheetState.INITIAL

    override fun onEvent(event: SampleBottomSheetEvents) {
        when (event) {
            is OnButton -> onNameChange()
            is Dismiss -> onDismiss()
            is OnDialog -> onDialog()
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

    private fun onDismiss() {
        doBack()
    }

    private fun onDialog() {
        updateDialog(VRODialogData(DIALOG))
    }

    companion object {
        private const val FIRST_TEXT = "Press to update"
        private const val SECOND_TEXT = "Press to update back"
        const val DIALOG = 1
    }
}