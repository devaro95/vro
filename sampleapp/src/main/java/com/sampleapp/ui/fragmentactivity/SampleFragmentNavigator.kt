package com.sampleapp.ui.fragmentactivity

import androidx.activity.ComponentActivity
import com.sampleapp.R
import com.vro.navigation.VROActivityNavigator
import com.vro.navigation.VRODestination

class SampleFragmentNavigator(
    activity: ComponentActivity,
) : VROActivityNavigator<SampleFragmentNavigator.SampleFragmentDestinations>(
    navHostId = R.id.navHostFragment,
    activity = activity
) {

    override fun navigate(destination: SampleFragmentDestinations) {

    }

    sealed class SampleFragmentDestinations : VRODestination()
}