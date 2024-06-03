package com.sampleapp.samplefragment

import androidx.fragment.app.Fragment
import com.sampleapp.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator

class SampleComposableFragmentNavigator(
    fragment: Fragment,
) : VROFragmentNavigator<SampleComposableFragmentDestinations>(fragment) {

    override fun navigate(destination: SampleComposableFragmentDestinations) {

    }

    sealed class SampleComposableFragmentDestinations : VRODestination()
}