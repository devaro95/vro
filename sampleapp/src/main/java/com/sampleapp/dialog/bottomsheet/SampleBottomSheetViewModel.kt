package com.sampleapp.dialog.bottomsheet

import com.vro.compose.VROComposableViewModel
import com.vro.navigation.VROEmptyDestination

class SampleBottomSheetViewModel :
    VROComposableViewModel<SampleBottomSheetState, VROEmptyDestination>(), SampleBottomSheetEvents {

    override val initialState = SampleBottomSheetState.INITIAL

    override fun onNameChange(name: String) {
        updateScreen {
            copy(
                name = name
            )
        }
    }
}