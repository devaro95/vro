package com.sampleapp.ui.samplefragment

import com.sampleapp.ui.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations
import com.vro.fragment.VROViewModel

class SampleComposableFragmentViewModel : VROViewModel<SampleComposableFragmentState, SampleComposableFragmentDestinations, SampleComposableFragmentEvents>() {

    override val initialViewState: SampleComposableFragmentState = SampleComposableFragmentState.INITIAL
    
    override fun eventListener(event: SampleComposableFragmentEvents) {

    }

}