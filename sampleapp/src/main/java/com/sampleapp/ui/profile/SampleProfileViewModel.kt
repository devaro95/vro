package com.sampleapp.ui.profile

import com.sampleapp.ui.base.SampleBaseViewModel
import com.sampleapp.ui.main.SampleDestinations

class SampleProfileViewModel : SampleBaseViewModel<SampleProfileState, SampleDestinations, SampleProfileEvents>() {

    override val initialState: SampleProfileState = SampleProfileState.INITIAL

    override fun onEvent(event: SampleProfileEvents) = Unit
}