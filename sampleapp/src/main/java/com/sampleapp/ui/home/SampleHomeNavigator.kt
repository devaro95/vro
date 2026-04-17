package com.sampleapp.ui.home

import androidx.navigation.NavController
import com.sampleapp.dialog.bottomsheet.SampleBottomSheet
import com.sampleapp.ui.detail.screen.SampleDetailScreen
import com.sampleapp.ui.detail.SampleDetailStarter
import com.sampleapp.ui.fragmentactivity.SampleFragmentActivity
import com.sampleapp.ui.home.screen.SampleHomeScreen
import com.sampleapp.ui.main.SampleDestinations
import com.sampleapp.ui.main.SampleDestinations.ActivityFragmentNavigation
import com.sampleapp.ui.main.SampleDestinations.BottomSheetNavigation
import com.sampleapp.ui.main.SampleDestinations.DetailNavigation
import com.sampleapp.ui.main.SampleDestinations.HomeNavigation
import com.sampleapp.ui.main.SampleDestinations.ProfileNavigation
import com.sampleapp.ui.main.SampleDestinations.TemplateNavigation
import com.sampleapp.ui.profile.screen.SampleProfileScreen
import com.sampleapp.ui.template.SampleTemplate
import com.vro.compose.VROComposableActivity
import com.vro.compose.navigator.VROComposableNavigator

class SampleHomeNavigator(
    override val activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleDestinations>(activity, navController) {

    override fun onDestination(destination: SampleDestinations) {
        when (destination) {
            HomeNavigation -> navigate(SampleHomeScreen::class)
            ProfileNavigation -> navigate(SampleProfileScreen::class)
            BottomSheetNavigation -> navigate(SampleBottomSheet::class)
            ActivityFragmentNavigation -> startActivity(SampleFragmentActivity.createIntent(activity.baseContext))
            TemplateNavigation -> navigate(SampleTemplate::class)
            is DetailNavigation -> navigate(
                destination = SampleDetailScreen::class,
                starter = SampleDetailStarter.Initial(state = destination.state)
            )
        }
    }
}