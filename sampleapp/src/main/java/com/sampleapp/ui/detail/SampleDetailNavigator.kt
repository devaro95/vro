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

    override fun navigate(destination: SampleDestinations) {
        when (destination) {
            SampleDestinations.ProfileNavigation -> navigateToScreen(SampleProfileScreen())
            else -> Unit
        }
    }
}