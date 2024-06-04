package com.sampleapp.home

import androidx.navigation.NavController
import com.sampleapp.detail.SampleDetailScreen
import com.sampleapp.dialog.bottomsheet.SampleBottomSheet
import com.sampleapp.fragmentactivity.SampleFragmentActivity
import com.sampleapp.main.SampleDestinations
import com.sampleapp.main.SampleDestinations.ActivityFragmentNavigation
import com.sampleapp.main.SampleDestinations.BottomSheetNavigation
import com.sampleapp.main.SampleDestinations.DetailNavigation
import com.sampleapp.main.SampleDestinations.HomeNavigation
import com.sampleapp.main.SampleDestinations.ProfileNavigation
import com.sampleapp.profile.SampleProfileScreen
import com.vro.compose.VROComposableActivity
import com.vro.compose.VROComposableNavigator

class SampleHomeNavigator(
    private val activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleDestinations>(activity, navController) {

    override fun navigate(destination: SampleDestinations) {
        when (destination) {
            HomeNavigation -> navigateToScreen(SampleHomeScreen())
            ProfileNavigation -> navigateToScreen(SampleProfileScreen())
            DetailNavigation -> navigateToScreen(SampleDetailScreen())
            BottomSheetNavigation -> openBottomSheet(SampleBottomSheet())
            ActivityFragmentNavigation -> activity.startActivity(SampleFragmentActivity.createIntent(activity.baseContext))
        }
    }
}