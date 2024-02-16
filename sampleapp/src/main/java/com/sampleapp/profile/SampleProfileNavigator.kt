package com.sampleapp.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import com.sampleapp.main.SampleDestinations
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableActivity

@ExperimentalMaterial3Api
class SampleProfileNavigator(
    activity: com.vro.compose.VROComposableActivity,
    navController: NavController,
) : com.vro.compose.VROComposableNavigator<SampleDestinations>(activity, navController) {

    override fun navigate(destination: SampleDestinations) {

    }
}