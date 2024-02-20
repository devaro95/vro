package com.vro.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.vro.compose.components.VROBottomBar
import com.vro.compose.components.VroTopBar
import com.vro.compose.extensions.destinationRoute
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.compose.states.VROComposableScaffoldState.VROBottomBarState
import com.vro.compose.states.VROComposableScaffoldState.VROTopBarState

abstract class VROComposableActivity : ComponentActivity() {

    open val theme: VROComposableTheme? = null

    abstract val startScreen: VROComposableScreen<*, *, *>

    private lateinit var navController: NavController

    abstract val bottomBarState: VROBottomBarState?

    @Composable
    private fun CreateTheme(
        lightColors: ColorScheme,
        darkColors: ColorScheme,
        typography: Typography,
        content: @Composable () -> Unit,
    ) {
        MaterialTheme(
            colorScheme = if (isSystemInDarkTheme()) darkColors else lightColors,
            typography = typography
        ) {
            CompositionLocalProvider(
                content = content
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            theme?.also {
                CreateTheme(it.lightColors, it.darkColors, it.typography) { Initialize() }
            } ?: run { Initialize() }
        }
    }

    @OptIn(ExperimentalMaterialNavigationApi::class)
    @Composable
    fun Initialize() {
        val bottomSheetNavigator = rememberBottomSheetNavigator()
        val navController = rememberNavController(bottomSheetNavigator)
        this.navController = navController
        val scaffoldState = remember { mutableStateOf(VROComposableScaffoldState()) }
        ModalBottomSheetLayout(
            modifier = Modifier.fillMaxSize(),
            bottomSheetNavigator = bottomSheetNavigator,
            sheetShape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = { TopBar(scaffoldState.value.topBarState) },
                bottomBar = { if (scaffoldState.value.showBottomBar) BottomBar() }
            ) { innerPadding ->
                Column(
                    modifier = Modifier.padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = if (bottomBarState == null) 0.dp
                        else innerPadding.calculateBottomPadding()
                    )
                ) {

                    NavHost(
                        navController = navController,
                        startDestination = startScreen.destinationRoute()
                    ) {
                        createComposableContent(
                            navController,
                            scaffoldState
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun TopBar(topBarState: VROTopBarState?) {
        topBarState?.let {
            VroTopBar(state = it)
        }
    }

    @Composable
    open fun BottomBar() {
        (bottomBarState)?.let {
            VROBottomBar(
                itemList = it.itemList,
                height = it.height,
                background = it.background
            )
        }
    }

    fun navigateToScreen(screen: VROComposableScreen<*, *, *>) {
        navController.navigate(screen.destinationRoute()) {
            popUpTo(navController.graph.id) { inclusive = true }
            launchSingleTop = true
        }
    }

    abstract fun NavGraphBuilder.createComposableContent(
        navController: NavHostController,
        scaffoldState: MutableState<VROComposableScaffoldState>,
    )
}