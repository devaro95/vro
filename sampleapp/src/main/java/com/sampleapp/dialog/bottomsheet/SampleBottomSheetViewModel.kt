package com.sampleapp.dialog.bottomsheet

import com.sampleapp.dialog.bottomsheet.SampleBottomSheetNavigator.SampleBottomSheetDestinations
import com.vro.compose.VROComposableViewModel

class SampleBottomSheetViewModel :
    VROComposableViewModel<SampleBottomSheetState, SampleBottomSheetDestinations>(), SampleBottomSheetEvents {

    override val initialState = SampleBottomSheetState.INITIAL

    override fun onNameChange(name: String) {
        updateScreen {
            copy(
                name = name
            )
        }
    }
}