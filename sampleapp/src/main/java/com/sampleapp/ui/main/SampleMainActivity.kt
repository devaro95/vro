package com.sampleapp.ui.main

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.sampleapp.R
import com.sampleapp.bottombar.SampleBottomBarState
import com.sampleapp.ui.detail.SampleDetailNavigator
import com.sampleapp.ui.detail.SampleDetailScreen
import com.sampleapp.ui.detail.SampleDetailViewModel
import com.sampleapp.dialog.bottomsheet.SampleBottomSheet
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetViewModel
import com.sampleapp.ui.home.SampleHomeNavigator
import com.sampleapp.ui.home.SampleHomeScreen
import com.sampleapp.ui.home.SampleHomeViewModel
import com.sampleapp.ui.profile.SampleProfileNavigator
import com.sampleapp.ui.profile.SampleProfileScreen
import com.sampleapp.ui.profile.SampleProfileViewModel
import com.sampleapp.styles.SampleTheme
import com.vro.compose.VROComposableActivity
import com.vro.compose.VROComposableTheme
import com.vro.compose.extensions.vroBottomSheet
import com.vro.compose.extensions.vroComposableScreen
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.compose.states.VROComposableScaffoldState.VROBottomBarState.VROBottomBarItem
import org.koin.androidx.compose.koinViewModel

class SampleMainActivity : VROComposableActivity() {

    override val startScreen = SampleHomeScreen()

    override val theme: VROComposableTheme = SampleTheme

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
            navigator = SampleHomeNavigator(this@SampleMainActivity, navController),
            content = SampleHomeScreen(),
            scaffoldState = scaffoldState,
            bottomBar = true
        )
        vroComposableScreen(
            viewModel = { koinViewModel<SampleProfileViewModel>() },
            navigator = SampleProfileNavigator(this@SampleMainActivity, navController),
            content = SampleProfileScreen(),
            scaffoldState = scaffoldState
        )
        vroComposableScreen(
            viewModel = { koinViewModel<SampleDetailViewModel>() },
            navigator = SampleDetailNavigator(this@SampleMainActivity, navController),
            content = SampleDetailScreen(),
            scaffoldState = scaffoldState
        )
        vroBottomSheet(
            content = SampleBottomSheet(),
            viewModel = { koinViewModel<SampleBottomSheetViewModel>() }
        )
    }
}