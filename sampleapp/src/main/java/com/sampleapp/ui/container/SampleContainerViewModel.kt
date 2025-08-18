package com.sampleapp.ui.container

import com.vro.viewmodel.VROViewModel

class SampleContainerViewModel :
    VROViewModel<SampleContainerState, SampleContainerDestinations, SampleContainerEvents>() {

    override val initialState: SampleContainerState = SampleContainerState.INITIAL

    override fun onEvent(event: SampleContainerEvents) {
    }

}