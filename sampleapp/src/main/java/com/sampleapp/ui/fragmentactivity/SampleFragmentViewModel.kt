package com.sampleapp.ui.fragmentactivity

import com.vro.fragment.VROViewModel

class SampleFragmentViewModel : VROViewModel<SampleFragmentState, SampleFragmentNavigator.SampleFragmentDestinations, SampleFragmentEvents>() {

    override val initialState = SampleFragmentState.INITIAL

    override fun onEvent(event: SampleFragmentEvents) {

    }
}