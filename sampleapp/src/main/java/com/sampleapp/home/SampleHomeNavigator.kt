package com.sampleapp.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import com.sampleapp.main.SampleDestinations
import com.sampleapp.main.SampleDestinations.Home
import com.sampleapp.main.SampleDestinations.Profile
import com.sampleapp.profile.SampleProfileScreen
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableActivity

@ExperimentalMaterial3Api
class SampleHomeNavigator(
    activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleDestinations>(activity, navController) {

    override fun navigate(destination: SampleDestinations) {
        when (destination) {
            Home -> navigateToScreen(SampleHomeScreen())
            Profile -> navigateToScreen(SampleProfileScreen())
        }
    }
}