package com.sampleapp.ui.samplefragment

import com.sampleapp.ui.samplefragment.SampleComposableFragmentEvents.NavigateDestination
import com.sampleapp.ui.samplefragment.SampleComposableFragmentEvents.Update
import com.sampleapp.ui.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations
import com.sampleapp.ui.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations.DestinationNavigation
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentState
import com.vro.compose.VROComposableViewModel
import com.vro.navigation.resultListener

class SampleComposableFragmentViewModel :
    VROComposableViewModel<SampleComposableFragmentState, SampleComposableFragmentDestinations, SampleComposableFragmentEvents>() {

    override val initialState: SampleComposableFragmentState = SampleComposableFragmentState.INITIAL

    override fun getResult() {
        resultListener("100") {
            //Do Something
        }
    }

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
        navigate(DestinationNavigation(SampleComposableDestinationFragmentState.INITIAL.copy(text = "Probando probando")))
    }

    companion object {
        const val FIRST_TEXT = "Press to update"
        const val SECOND_TEXT = "Press to update back"
    }
}