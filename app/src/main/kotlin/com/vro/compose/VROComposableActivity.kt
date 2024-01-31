package com.vro.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vro.compose.components.VROBottomBar
import com.vro.compose.components.VroTopBar
import com.vro.compose.extensions.destinationRoute
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.compose.states.VROComposableScaffoldState.VROBottomBarState
import com.vro.compose.states.VROComposableScaffoldState.VROTopBarState

@ExperimentalMaterial3Api
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

    @Composable
    fun Initialize() {
        val navController = rememberNavController()
        this.navController = navController
        val scaffoldState = remember { mutableStateOf(VROComposableScaffoldState()) }
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { TopBar(scaffoldState.value.topBarState) },
            bottomBar = { BottomBar() }
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

    @Composable
    fun TopBar(topBarState: VROTopBarState?) {
        topBarState?.let {
            VroTopBar(
                title = it.title,
                titleSize = 14.sp,
                actionButton = it.actionButton,
                navigationButton = it.navigationButton
            )
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
        navController.navigate(screen.destinationRoute())
    }

    abstract fun NavGraphBuilder.createComposableContent(
        navController: NavHostController,
        scaffoldState: MutableState<VROComposableScaffoldState>,
    )
}