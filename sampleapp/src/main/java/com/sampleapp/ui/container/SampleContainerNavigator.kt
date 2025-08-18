package com.sampleapp.ui.container

import androidx.navigation.NavController
import com.vro.compose.VROComposableActivity
import com.vro.compose.navigator.VROComposableNavigator
import com.vro.navigation.VRODestination

class SampleContainerNavigator(
    activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleContainerDestinations>(activity, navController) {

    override fun navigate(destination: SampleContainerDestinations) = Unit
}

sealed class SampleContainerDestinations : VRODestination()