package com.sampleapp.ui.samplefragment

import com.sampleapp.ui.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations
import com.vro.viewmodel.VROViewModel

class SampleComposableFragmentViewModel : VROViewModel<SampleComposableFragmentState, SampleComposableFragmentDestinations, SampleComposableFragmentEvents>() {

    override val initialState = SampleComposableFragmentState.INITIAL

    override fun eventListener(event: SampleComposableFragmentEvents) {

    }

}