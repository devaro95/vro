package com.sampleapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.sampleapp.bottombar.SampleBottomBar
import com.sampleapp.dialog.bottomsheet.*
import com.sampleapp.styles.colors.SampleColorScheme
import com.sampleapp.styles.theme.*
import com.sampleapp.ui.detail.SampleDetailNavigator
import com.sampleapp.ui.detail.SampleDetailViewModel
import com.sampleapp.ui.detail.screen.SampleDetailScreen
import com.sampleapp.ui.home.SampleHomeNavigator
import com.sampleapp.ui.home.SampleHomeViewModel
import com.sampleapp.ui.home.screen.SampleHomeScreen
import com.sampleapp.ui.profile.SampleProfileNavigator
import com.sampleapp.ui.profile.SampleProfileViewModel
import com.sampleapp.ui.profile.screen.SampleProfileScreen
import com.sampleapp.ui.template.*
import com.vro.compose.VROComposableActivity
import com.vro.compose.extensions.*
import com.vro.compose.states.*
import com.vro.compose.theme.VROComposableMaterialTheme
import com.vro.core_android.injection.injectViewModel

open class SampleMainActivity : VROComposableActivity() {

    override val startScreen = SampleHomeScreen()

    override val materialTheme: VROComposableMaterialTheme = SampleTheme

    override val customTheme: SampleComposableCustomTheme = SampleCustomTheme

    companion object {
        val LocalActivityColors = staticCompositionLocalOf<SampleColorScheme> {
            error("No AppColors provided")
        }
    }

    @Composable
    override fun getProvidedValues(): List<ProvidedValue<*>> {
        val colors = (if (isSystemInDarkTheme()) customTheme.darkColors else customTheme.lightColors) ?: customTheme.lightColors
        return listOf(
            LocalActivityColors provides colors
        )
    }

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
        vroComposableTemplate(
            viewModel = { injectViewModel<SampleTemplateViewModel>() },
            navigator = SampleTemplateNavigator(this@SampleMainActivity, navController),
            content = SampleTemplate(),
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