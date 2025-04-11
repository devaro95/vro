package com.sampleapp.dialog.bottomsheet

import androidx.navigation.NavController
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetNavigator.SampleBottomSheetDestinations
import com.sampleapp.ui.main.SampleMainActivity
import com.vro.compose.navigator.VROComposableNavigator
import com.vro.navigation.VRODestination

class SampleBottomSheetNavigator(
    activity: SampleMainActivity,
    navController: NavController,
) : VROComposableNavigator<SampleBottomSheetDestinations>(activity, navController) {

    override fun navigate(destination: SampleBottomSheetDestinations) {

    }

    sealed class SampleBottomSheetDestinations : VRODestination()
}