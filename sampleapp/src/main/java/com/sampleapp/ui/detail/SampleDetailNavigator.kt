package com.sampleapp.ui.detail

import androidx.navigation.NavController
import com.sampleapp.ui.main.SampleDestinations
import com.sampleapp.ui.profile.SampleProfileScreen
import com.vro.compose.VROComposableActivity
import com.vro.compose.VROComposableNavigator

class SampleDetailNavigator(
    activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleDestinations>(activity, navController) {

    override fun navigate(destination: SampleDestinations) {
        when (destination) {
            SampleDestinations.BottomSheetNavigation -> Unit
            is SampleDestinations.DetailNavigation -> Unit
            SampleDestinations.HomeNavigation -> Unit
            SampleDestinations.ProfileNavigation -> navigateToScreen(SampleProfileScreen())
            SampleDestinations.ActivityFragmentNavigation -> Unit
        }
    }
}