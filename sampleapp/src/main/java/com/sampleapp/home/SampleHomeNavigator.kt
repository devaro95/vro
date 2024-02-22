package com.sampleapp.home

import androidx.navigation.NavController
import com.sampleapp.detail.SampleDetailScreen
import com.sampleapp.dialog.bottomsheet.SampleBottomSheet
import com.sampleapp.main.SampleDestinations
import com.sampleapp.main.SampleDestinations.BottomSheet
import com.sampleapp.main.SampleDestinations.Detail
import com.sampleapp.main.SampleDestinations.Home
import com.sampleapp.main.SampleDestinations.Profile
import com.sampleapp.profile.SampleProfileScreen
import com.vro.compose.VROComposableActivity
import com.vro.compose.VROComposableNavigator

class SampleHomeNavigator(
    activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleDestinations>(activity, navController) {

    override fun navigate(destination: SampleDestinations) {
        when (destination) {
            Home -> navigateToScreen(SampleHomeScreen())
            Profile -> navigateToScreen(SampleProfileScreen())
            Detail -> navigateToScreen(SampleDetailScreen())
            BottomSheet -> openBottomSheet(SampleBottomSheet())
        }
    }
}