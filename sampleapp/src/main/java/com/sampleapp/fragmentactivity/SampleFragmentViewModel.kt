package com.sampleapp.fragmentactivity

import com.vro.fragment.VROViewModel

class SampleFragmentViewModel : VROViewModel<SampleFragmentState, SampleFragmentNavigator.SampleFragmentDestinations, SampleFragmentEvents>() {

    override val initialViewState = SampleFragmentState.INITIAL

    override fun eventListener(event: SampleFragmentEvents) {

    }
}