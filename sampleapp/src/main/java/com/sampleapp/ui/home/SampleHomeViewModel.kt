package com.sampleapp.ui.home

import com.sampleapp.ui.base.SampleBaseViewModel
import com.sampleapp.ui.home.SampleHomeEvents.ActivityFragment
import com.sampleapp.ui.home.SampleHomeEvents.BottomSheetDismiss
import com.sampleapp.ui.home.SampleHomeEvents.Detail
import com.sampleapp.ui.home.SampleHomeEvents.HideDialog
import com.sampleapp.ui.home.SampleHomeEvents.OneTimeLaunch
import com.sampleapp.ui.home.SampleHomeEvents.Profile
import com.sampleapp.ui.home.SampleHomeEvents.ShowBottomSheet
import com.sampleapp.ui.home.SampleHomeEvents.ShowNavigationBottomSheet
import com.sampleapp.ui.home.SampleHomeEvents.ShowSimpleDialog
import com.sampleapp.ui.home.SampleHomeEvents.ShowSimpleDialogWithViewModel
import com.sampleapp.ui.home.SampleHomeEvents.UpdateText
import com.sampleapp.ui.home.SampleHomeEvents.VmDialogDismiss
import com.sampleapp.ui.main.SampleDestinations
import com.sampleapp.ui.main.SampleDestinations.ActivityFragmentNavigation
import com.sampleapp.ui.main.SampleDestinations.BottomSheetNavigation
import com.vro.state.VRODialogState

class SampleHomeViewModel : SampleBaseViewModel<SampleHomeState, SampleDestinations, SampleHomeEvents>() {

    override val initialState: SampleHomeState = SampleHomeState.INITIAL

    override fun eventListener(event: SampleHomeEvents) {
        when (event) {
            BottomSheetDismiss -> onActionBottomSheetDismiss()
            Detail -> onActionDetailNavigationClick()
            HideDialog -> onActionHideDialog()
            Profile -> navigateToProfile()
            ShowNavigationBottomSheet -> onActionShowNavigationBottomSheetClick()
            ShowBottomSheet -> onActionShowBottomSheetClick()
            ShowSimpleDialog -> onActionShowSimpleDialogClick()
            ShowSimpleDialogWithViewModel -> onActionShowSimpleDialogWithViewModelClick()
            UpdateText -> onActionUpdateTextClick()
            VmDialogDismiss -> onVmDialogDismiss()
            ActivityFragment -> onActivityFragmentClick()
            OneTimeLaunch -> onOneTimeLaunchClick()
        }
    }

    override fun onStart() {
        updateScreen {
            copy(text = FIRST_TEXT)
        }
    }

    private fun onActionShowNavigationBottomSheetClick() {
        navigate(BottomSheetNavigation)
    }

    private fun onActionShowBottomSheetClick() {
        updateDialog(VRODialogState(BOTTOM_SHEET))
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
                        else -> FIRST_TEXT
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

    private fun onOneTimeLaunchClick() {
        updateOneTime(id = ONE_TIME_LAUNCH, checkDataState())
    }

    companion object {
        const val FIRST_TEXT = "Press to update"
        const val SECOND_TEXT = "Press to update back"
        const val SIMPLE_VIEW_MODEL_DIALOG = 1
        const val BOTTOM_SHEET = 2
        const val ONE_TIME_LAUNCH = 1
    }
}