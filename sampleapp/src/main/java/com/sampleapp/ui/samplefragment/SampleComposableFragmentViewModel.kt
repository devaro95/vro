package com.sampleapp.ui.samplefragment

import com.sampleapp.ui.samplefragment.SampleComposableFragmentEvents.NavigateDestination
import com.sampleapp.ui.samplefragment.SampleComposableFragmentEvents.Update
import com.sampleapp.ui.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations
import com.sampleapp.ui.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations.DestinationNavigation
import com.vro.viewmodel.VROViewModel

class SampleComposableFragmentViewModel :
    VROViewModel<SampleComposableFragmentState, SampleComposableFragmentDestinations, SampleComposableFragmentEvents>() {

    override val initialState: SampleComposableFragmentState = SampleComposableFragmentState.INITIAL
    

    override fun onEvent(event: SampleComposableFragmentEvents) {
        when (event) {
            Update -> onUpdateButtonText()
            NavigateDestination -> onNavigateDestination()
        }
    }

    private fun onUpdateButtonText() {
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

    private fun onNavigateDestination() {
        navigate(DestinationNavigation)
    }

    companion object {
        const val FIRST_TEXT = "Press to update"
        const val SECOND_TEXT = "Press to update back"
    }
}