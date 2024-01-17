package com.sampleapp.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.sampleapp.home.SampleHomeNavigator
import com.sampleapp.home.SampleHomeScreen
import com.sampleapp.home.SampleHomeViewModel
import com.sampleapp.profile.SampleProfileNavigator
import com.sampleapp.profile.SampleProfileScreen
import com.sampleapp.profile.SampleProfileViewModel
import com.sampleapp.styles.SampleTheme
import com.vro.compose.VROComposableTheme
import com.vro.compose.VROComposableActivity
import com.vro.compose.extensions.vroComposableScreen
import com.vro.compose.states.VROComposableScaffoldState
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
class SampleMainActivity : VROComposableActivity() {

    override val startScreen = SampleHomeScreen()

    override val theme: VROComposableTheme = SampleTheme

    override fun NavGraphBuilder.createComposableContent(
        navController: NavHostController,
        scaffoldState: MutableState<VROComposableScaffoldState>,
    ) {
        vroComposableScreen(
            viewModel = { koinViewModel<SampleHomeViewModel>() },
            navController = navController,
            navigator = SampleHomeNavigator(this@SampleMainActivity, navController),
            content = SampleHomeScreen(),
            scaffoldState = scaffoldState
        )
        vroComposableScreen(
            viewModel = { koinViewModel<SampleProfileViewModel>() },
            navController = navController,
            navigator = SampleProfileNavigator(this@SampleMainActivity, navController),
            content = SampleProfileScreen(),
            scaffoldState = scaffoldState
        )
    }
}