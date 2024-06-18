package com.sampleapp.ui.fragmentactivity

import android.app.Activity
import com.sampleapp.R
import com.vro.navigation.*

class SampleFragmentNavigator(
    activity: Activity,
) : VROActivityNavigator<SampleFragmentNavigator.SampleFragmentDestinations>(activity, R.id.navHostFragment) {

    override fun navigate(destination: SampleFragmentDestinations) {

    }

    sealed class SampleFragmentDestinations : VRODestination()
}