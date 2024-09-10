package com.sampleapp.ui.samplefragment.destination

import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentEvents.BackWithResult
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentEvents.Update
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentNavigator.SampleComposableDestinationFragmentDestinations
import com.vro.compose.VROComposableViewModel
import com.vro.navigation.VROBackResult
import com.vro.navstarter.VRONavStarter

class SampleComposableDestinationFragmentViewModel :
    VROComposableViewModel<SampleComposableDestinationFragmentState, SampleComposableDestinationFragmentDestinations, SampleComposableDestinationFragmentEvents>() {

    override val initialState: SampleComposableDestinationFragmentState = SampleComposableDestinationFragmentState.INITIAL

    override fun onNavParam(navParam: VRONavStarter?) {
        when (navParam) {
            is SampleComposableDestinationStarter.Initial -> updateScreen { navParam.state }
        }
    }

    override fun eventListener(event: SampleComposableDestinationFragmentEvents) {
        when (event) {
            BackWithResult -> eventBack(VROBackResult(100))
            Update -> Unit
        }
    }
}