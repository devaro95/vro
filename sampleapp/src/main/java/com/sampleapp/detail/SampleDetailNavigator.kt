package com.sampleapp.detail

import androidx.navigation.NavController
import com.sampleapp.main.SampleDestinations
import com.vro.compose.VROComposableActivity
import com.vro.compose.VROComposableNavigator

class SampleDetailNavigator(
    activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<SampleDestinations>(activity, navController) {

    override fun navigate(destination: SampleDestinations) {

    }
}