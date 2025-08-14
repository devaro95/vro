package com.sampleapp.ui.samplefragment.destination

import com.sampleapp.ui.home.SampleHomeViewModel.Companion.FIRST_TEXT
import com.sampleapp.ui.home.SampleHomeViewModel.Companion.SECOND_TEXT
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentEvents.Update
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentNavigator.SampleComposableDestinationFragmentDestinations
import com.vro.viewmodel.VROViewModel
import com.vro.navstarter.VRONavStarter

class SampleComposableDestinationFragmentViewModel :
    VROViewModel<SampleComposableDestinationFragmentState, SampleComposableDestinationFragmentDestinations, SampleComposableDestinationFragmentEvents>() {

    override val initialState: SampleComposableDestinationFragmentState =
        SampleComposableDestinationFragmentState.INITIAL

    override fun onStarter(starter: VRONavStarter?) {
        when (starter) {
            is SampleComposableDestinationStarter.Initial -> updateScreen { starter.state }
        }
    }

    override fun onEvent(event: SampleComposableDestinationFragmentEvents) {
        when (event) {
            Update -> {
                checkDataState().also {
                    updateScreen {
                        copy(
                            text = when (it.text) {
                                FIRST_TEXT -> SECOND_TEXT
                                SECOND_TEXT -> FIRST_TEXT
                                else -> FIRST_TEXT
                            }
                        )
                    }
                }
            }
        }
    }
}