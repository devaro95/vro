package com.sampleapp.ui.samplefragment.destination

import androidx.fragment.app.Fragment
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentNavigator.SampleComposableDestinationFragmentDestinations
import com.vro.core_android.navigation.VROFragmentNavigator
import com.vro.navigation.VRODestination

class SampleComposableDestinationFragmentNavigator(
    fragment: Fragment,
) : VROFragmentNavigator<SampleComposableDestinationFragmentDestinations>(fragment) {

    override fun navigate(destination: SampleComposableDestinationFragmentDestinations) {
    }

    sealed class SampleComposableDestinationFragmentDestinations : VRODestination()
}