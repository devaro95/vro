package com.sampleapp.home

import com.sampleapp.base.SampleBaseViewModel
import com.sampleapp.main.SampleDestinations
import com.vro.constants.EMPTY_STRING
import com.vro.navigation.VROBackResult
import com.vro.state.VRODialogState
import java.io.Serializable

class SampleHomeViewModel : SampleBaseViewModel<SampleHomeState, SampleDestinations>(), SampleHomeEvents {

    override val initialState: SampleHomeState = SampleHomeState.INITIAL

    override suspend fun onStart() {
        updateScreen {
            copy(text = FIRST_TEXT)
        }
    }

    override fun onActionShowBottomSheetClick() {
        navigate(SampleDestinations.BottomSheet)
    }

    override fun onActionShowSimpleDialogClick() {
        showSimpleDialog()
    }

    override fun onActionUpdateTextClick() {
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

    override fun onActionBottomSheetDismiss() {
        updateScreen()
    }

    override fun navigateToProfile() {
        navigate(SampleDestinations.Profile)
    }

    override fun onActionHideDialog() {
        updateScreen()
    }

    override fun onActionProfileNavigationClick(){
        navigate(SampleDestinations.Profile)
    }
    override fun onActionDetailNavigationClick(){
        navigate(SampleDestinations.Detail)
    }

    companion object {
        const val FIRST_TEXT = "This is a text"
        const val SECOND_TEXT = "Another text"
    }
}