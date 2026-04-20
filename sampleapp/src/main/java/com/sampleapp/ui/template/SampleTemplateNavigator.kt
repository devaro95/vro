package com.sampleapp.ui.template

import androidx.navigation.NavController
import com.vro.compose.VROComposableActivity
import com.vro.compose.navigator.VROComposableNavigator
import com.vro.navigation.VRODestination

class SampleTemplateNavigator(
    activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleTemplateDestinations>(activity, navController) {
    override fun onDestination(destination: SampleTemplateDestinations) {

    }
}

sealed class SampleTemplateDestinations : VRODestination()