package com.sampleapp.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.sampleapp.R
import com.sampleapp.bottombar.SampleBottomBarState
import com.sampleapp.home.SampleHomeNavigator
import com.sampleapp.home.SampleHomeScreen
import com.sampleapp.home.SampleHomeViewModel
import com.sampleapp.profile.SampleProfileNavigator
import com.sampleapp.profile.SampleProfileScreen
import com.sampleapp.profile.SampleProfileViewModel
import com.sampleapp.styles.SampleTheme
import com.vro.compose.extensions.vroComposableScreen
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.compose.states.VROComposableScaffoldState.VROBottomBarState.VROBottomBarItem
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
class SampleMainActivity : com.vro.compose.VROComposableActivity() {

    override val startScreen = SampleHomeScreen()

    override val theme: com.vro.compose.VROComposableTheme = SampleTheme

    override val bottomBarState = SampleBottomBarState(
        items = listOf(
            VROBottomBarItem(
                icon = R.drawable.ic_profile,
                iconSelected = R.drawable.ic_arrow,
            ),
            VROBottomBarItem(
                icon = R.drawable.ic_profile,
                iconSelected = R.drawable.ic_profile,
                onClick = { navigateToScreen(SampleProfileScreen()) }
            )
        )
    )

    override fun NavGraphBuilder.createComposableContent(
        navController: NavHostController,
        scaffoldState: MutableState<VROComposableScaffoldState>,
    ) {
        vroComposableScreen(
            viewModel = { koinViewModel<SampleHomeViewModel>() },
            navController = navController,
            navigator = SampleHomeNavigator(this@SampleMainActivity, navController),
            content = SampleHomeScreen(),
            scaffoldState = scaffoldState,
            bottomBar = true
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