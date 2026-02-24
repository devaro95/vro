package com.sampleapp.ui.detail

import androidx.navigation.NavController
import com.sampleapp.ui.main.SampleDestinations
import com.sampleapp.ui.profile.screen.SampleProfileScreen
import com.vro.compose.VROComposableActivity
import com.vro.compose.navigator.VROComposableNavigator

class SampleDetailNavigator(
    activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleDestinations>(activity, navController) {

    override fun onDestination(destination: SampleDestinations) {
        when (destination) {
            SampleDestinations.ProfileNavigation -> navigate(SampleProfileScreen::class)
            else -> Unit
        }
    }
}