package com.sampleapp.home

import com.sampleapp.base.SampleBaseViewModel
import com.sampleapp.home.SampleHomeEvents.ActivityFragment
import com.sampleapp.home.SampleHomeEvents.BottomSheetDismiss
import com.sampleapp.home.SampleHomeEvents.DetailNavigation
import com.sampleapp.home.SampleHomeEvents.HideDialog
import com.sampleapp.home.SampleHomeEvents.ProfileNavigation
import com.sampleapp.home.SampleHomeEvents.ShowBottomSheetClick
import com.sampleapp.home.SampleHomeEvents.ShowNavigationBottomSheetClick
import com.sampleapp.home.SampleHomeEvents.ShowSimpleDialogClick
import com.sampleapp.home.SampleHomeEvents.ShowSimpleDialogWithViewModelClick
import com.sampleapp.home.SampleHomeEvents.UpdateTextClick
import com.sampleapp.home.SampleHomeEvents.VmDialogDismiss
import com.sampleapp.main.SampleDestinations
import com.sampleapp.main.SampleDestinations.ActivityFragmentNavigation
import com.sampleapp.main.SampleDestinations.BottomSheetNavigation
import com.vro.constants.EMPTY_STRING
import com.vro.state.VRODialogState

class SampleHomeViewModel : SampleBaseViewModel<SampleHomeState, SampleDestinations, SampleHomeEvents>() {

    override val initialState: SampleHomeState = SampleHomeState.INITIAL

    override fun eventListener(event: SampleHomeEvents) {
        when (event) {
            BottomSheetDismiss -> onActionBottomSheetDismiss()
            DetailNavigation -> onActionDetailNavigationClick()
            HideDialog -> onActionHideDialog()
            ProfileNavigation -> navigateToProfile()
            ShowNavigationBottomSheetClick -> onActionShowNavigationBottomSheetClick()
            ShowBottomSheetClick -> onActionShowBottomSheetClick()
            ShowSimpleDialogClick -> onActionShowSimpleDialogClick()
            ShowSimpleDialogWithViewModelClick -> onActionShowSimpleDialogWithViewModelClick()
            UpdateTextClick -> onActionUpdateTextClick()
            VmDialogDismiss -> onVmDialogDismiss()
            ActivityFragment -> onActivityFragmentClick()
        }
    }

    override suspend fun onStart() {
        updateScreen {
            copy(text = FIRST_TEXT)
        }
    }

    private fun onActionShowNavigationBottomSheetClick() {
        navigate(BottomSheetNavigation)
    }

    private fun onActionShowBottomSheetClick() {
        updateDialog(
            VRODialogState(BOTTOM_SHEET)
        )
    }

    private fun onActionShowSimpleDialogClick() {
        showSimpleDialog()
    }

    private fun onActionShowSimpleDialogWithViewModelClick() {
        updateDialog(
            VRODialogState(SIMPLE_VIEW_MODEL_DIALOG)
        )
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
        navigate(SampleDestinations.ProfileNavigation)
    }

    private fun onActionHideDialog() {
        updateScreen()
    }

    private fun onActionDetailNavigationClick() {
        navigate(SampleDestinations.DetailNavigation)
    }

    private fun onVmDialogDismiss() {
        updateScreen()
    }

    private fun onActivityFragmentClick() {
        navigate(ActivityFragmentNavigation)
    }

    companion object {
        private const val FIRST_TEXT = "Press to update"
        private const val SECOND_TEXT = "Press to update back"
        const val SIMPLE_VIEW_MODEL_DIALOG = 1
        const val BOTTOM_SHEET = 2
    }
}