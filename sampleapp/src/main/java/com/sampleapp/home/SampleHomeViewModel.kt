package com.sampleapp.home

import com.sampleapp.base.SampleBaseViewModel
import com.sampleapp.home.SampleHomeEvents.*
import com.sampleapp.main.SampleDestinations
import com.vro.constants.EMPTY_STRING
import com.vro.navigation.VROBackResult
import com.vro.state.VRODialogState
import java.io.Serializable

class SampleHomeViewModel : SampleBaseViewModel<SampleHomeState, SampleDestinations, SampleHomeEvents>() {

    override val initialState: SampleHomeState = SampleHomeState.INITIAL

    override fun eventListener(event: SampleHomeEvents) {
        when (event) {
            BottomSheetDismiss -> onActionBottomSheetDismiss()
            DetailNavigation -> onActionDetailNavigationClick()
            HideDialog -> onActionHideDialog()
            ProfileNavigation -> navigateToProfile()
            ShowBottomSheetClick -> onActionShowBottomSheetClick()
            ShowSimpleDialogClick -> onActionShowSimpleDialogClick()
            UpdateTextClick -> onActionUpdateTextClick()
        }
    }

    override suspend fun onStart() {
        updateScreen {
            copy(text = FIRST_TEXT)
        }
    }

    private fun onActionShowBottomSheetClick() {
        navigate(SampleDestinations.BottomSheet)
    }

    private fun onActionShowSimpleDialogClick() {
        showSimpleDialog()
    }

    private fun onActionUpdateTextClick() {
        checkDataState().also {
            updateScreen {
                copy(
                    text = when (it.text) {
                        FIRST_TEXT -> SECOND_TEXT
                        SECOND_TEXT -> FIRST_TEXT
                        else -> EMPTY_STRING
                    }
                )
            }
        }
    }

    private fun onActionBottomSheetDismiss() {
        updateScreen()
    }

    private fun navigateToProfile() {
        navigate(SampleDestinations.Profile)
    }

    private fun onActionHideDialog() {
        updateScreen()
    }

    private fun onActionDetailNavigationClick() {
        navigate(SampleDestinations.Detail)
    }

    companion object {
        private const val FIRST_TEXT = "Press to update"
        private const val SECOND_TEXT = "Press to update back"
    }
}