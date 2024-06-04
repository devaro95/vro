package com.sampleapp.samplefragment

import com.sampleapp.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations
import com.vro.fragment.VROViewModel

class SampleComposableFragmentViewModel : VROViewModel<SampleComposableFragmentState, SampleComposableFragmentDestinations, SampleComposableFragmentEvents>() {

    override val initialViewState: SampleComposableFragmentState = SampleComposableFragmentState.INITIAL
    
    override fun eventListener(event: SampleComposableFragmentEvents) {

    }

}