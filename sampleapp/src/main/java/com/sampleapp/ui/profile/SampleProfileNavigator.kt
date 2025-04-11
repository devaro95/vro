package com.sampleapp.ui.profile

import androidx.navigation.NavController
import com.sampleapp.ui.main.SampleDestinations
import com.vro.compose.VROComposableActivity
import com.vro.compose.navigator.VROComposableNavigator

class SampleProfileNavigator(
    activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleDestinations>(activity, navController) {

    override fun navigate(destination: SampleDestinations) = Unit
}