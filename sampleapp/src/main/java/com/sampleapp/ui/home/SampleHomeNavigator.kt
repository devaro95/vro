package com.sampleapp.ui.home

import androidx.navigation.NavController
import com.sampleapp.ui.detail.SampleDetailScreen
import com.sampleapp.dialog.bottomsheet.SampleBottomSheet
import com.sampleapp.ui.fragmentactivity.SampleFragmentActivity
import com.sampleapp.ui.main.SampleDestinations
import com.sampleapp.ui.main.SampleDestinations.ActivityFragmentNavigation
import com.sampleapp.ui.main.SampleDestinations.BottomSheetNavigation
import com.sampleapp.ui.main.SampleDestinations.DetailNavigation
import com.sampleapp.ui.main.SampleDestinations.HomeNavigation
import com.sampleapp.ui.main.SampleDestinations.ProfileNavigation
import com.sampleapp.ui.profile.SampleProfileScreen
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