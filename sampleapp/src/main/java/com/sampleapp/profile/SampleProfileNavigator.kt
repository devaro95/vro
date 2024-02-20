package com.sampleapp.profile

import androidx.navigation.NavController
import com.sampleapp.main.SampleDestinations
import com.vro.compose.VROComposableActivity
import com.vro.compose.VROComposableNavigator

class SampleProfileNavigator(
    activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleDestinations>(activity, navController) {

    override fun navigate(destination: SampleDestinations) {

    }
}