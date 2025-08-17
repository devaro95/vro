package com.sampleapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.sampleapp.bottombar.SampleBottomBar
import com.sampleapp.dialog.bottomsheet.SampleBottomSheet
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetNavViewModel
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetNavigator
import com.sampleapp.styles.SampleTheme
import com.sampleapp.ui.container.SampleContainerNavigator
import com.sampleapp.ui.container.SampleContainerViewModel
import com.sampleapp.ui.container.screen.SampleContainerScreen
import com.sampleapp.ui.detail.*
import com.sampleapp.ui.detail.screen.SampleDetailScreen
import com.sampleapp.ui.home.*
import com.sampleapp.ui.home.screen.SampleHomeScreen
import com.sampleapp.ui.profile.*
import com.sampleapp.ui.profile.screen.SampleProfileScreen
import com.vro.compose.VROComposableActivity
import com.vro.compose.VROComposableTheme
import com.vro.compose.extensions.vroBottomSheet
import com.vro.compose.extensions.vroComposableScreen
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.compose.states.VROSnackBarState
import com.vro.compose.states.VROTopBarBaseState
import com.vro.core_android.injection.injectViewModel

class SampleMainActivity : VROComposableActivity() {

    override val startScreen = SampleHomeScreen()

    override val theme: VROComposableTheme = SampleTheme

    @Composable
    override fun BottomBar(selectedItem: Int) {
        Column {
            HorizontalDivider(Modifier.background(Color.Gray), thickness = 1.dp)
            SampleBottomBar(
                onHomeClick = { navigateToScreen(SampleHomeScreen()) },
                onProfileClick = { navigateToScreen(SampleProfileScreen()) },
                selectedItem = selectedItem
            )
        }
    }

    override fun NavGraphBuilder.createComposableContent(
        navController: NavHostController,
        topBarState: MutableState<VROTopBarBaseState>,
        bottomBarState: MutableState<VROBottomBarBaseState>,
        snackbarState: MutableState<VROSnackBarState>,
    ) {
        vroComposableScreen(
            viewModel = { injectViewModel<SampleHomeViewModel>() },
            navigator = SampleHomeNavigator(this@SampleMainActivity, navController),
            content = SampleHomeScreen(),
            topBarState = topBarState,
            bottomBarState = bottomBarState,
            snackbarState = snackbarState
        )
        vroComposableScreen(
            viewModel = { injectViewModel<SampleProfileViewModel>() },
            navigator = SampleProfileNavigator(this@SampleMainActivity, navController),
            content = SampleProfileScreen(),
            topBarState = topBarState,
            bottomBarState = bottomBarState,
            snackbarState = snackbarState
        )
        vroComposableScreen(
            viewModel = { injectViewModel<SampleContainerViewModel>() },
            navigator = SampleContainerNavigator(this@SampleMainActivity, navController),
            content = SampleContainerScreen(),
            topBarState = topBarState,
            bottomBarState = bottomBarState,
            snackbarState = snackbarState
        )
        vroComposableScreen(
            viewModel = { injectViewModel<SampleDetailViewModel>() },
            navigator = SampleDetailNavigator(this@SampleMainActivity, navController),
            content = SampleDetailScreen(),
            topBarState = topBarState,
            bottomBarState = bottomBarState,
            snackbarState = snackbarState
        )
        vroBottomSheet(
            content = SampleBottomSheet(),
            viewModel = { injectViewModel<SampleBottomSheetNavViewModel>() },
            navigator = SampleBottomSheetNavigator(this@SampleMainActivity, navController)
        )
    }
}