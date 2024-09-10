package com.sampleapp.ui.samplefragment

import androidx.fragment.app.Fragment
import com.sampleapp.R
import com.sampleapp.ui.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations
import com.sampleapp.ui.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations.DestinationNavigation
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentState
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationStarter
import com.vro.core_android.navigation.VROFragmentNavigator
import com.vro.navigation.VRODestination

class SampleComposableFragmentNavigator(
    fragment: Fragment,
) : VROFragmentNavigator<SampleComposableFragmentDestinations>(fragment) {

    override fun navigate(destination: SampleComposableFragmentDestinations) {
        when (destination) {
            is DestinationNavigation -> navigateWithAction(
                action = R.id.action_sampleComposableFragment_to_sampleComposableDestinationFragment,
                starter = SampleComposableDestinationStarter.Initial(destination.state)
            )
        }
    }

    sealed class SampleComposableFragmentDestinations : VRODestination() {
        data class DestinationNavigation(val state: SampleComposableDestinationFragmentState) : SampleComposableFragmentDestinations()
    }
}