package com.sampleapp.detail

import androidx.navigation.NavController
import com.sampleapp.main.SampleDestinations
import com.sampleapp.profile.SampleProfileScreen
import com.vro.compose.VROComposableActivity
import com.vro.compose.VROComposableNavigator

class SampleDetailNavigator(
    activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleDestinations>(activity, navController) {

    override fun navigate(destination: SampleDestinations) {
        when (destination) {
            SampleDestinations.BottomSheet -> Unit
            SampleDestinations.Detail -> Unit
            SampleDestinations.Home -> Unit
            SampleDestinations.Profile -> navigateToScreen(SampleProfileScreen())
        }
    }
}