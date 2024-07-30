package com.vro.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.*
import com.vro.compose.components.VroTopBar
import com.vro.compose.extensions.destinationRoute
import com.vro.compose.screen.VROScreen
import com.vro.compose.states.VROBottomBarState
import com.vro.compose.states.VROTopBarState

abstract class VROComposableActivity : ComponentActivity() {

    open val theme: VROComposableTheme? = null

    abstract val startScreen: VROScreen<*, *>

    private lateinit var navController: NavController

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
                CreateTheme(it.lightColors, it.darkColors, it.typography) { Initialize(MaterialTheme.colorScheme.background) }
            } ?: run { Initialize() }
        }
    }

    @OptIn(ExperimentalMaterialNavigationApi::class)
    @Composable
    fun Initialize(backgroundColor: Color? = null) {
        val bottomSheetNavigator = rememberBottomSheetNavigator()
        val navController = rememberNavController(bottomSheetNavigator)
        this.navController = navController
        val topBarState = remember { mutableStateOf<VROTopBarState?>(VROTopBarState()) }
        val bottomBarState = remember { mutableStateOf<VROBottomBarState?>(VROBottomBarState()) }
        ModalBottomSheetLayout(
            modifier = Modifier.fillMaxSize(),
            bottomSheetNavigator = bottomSheetNavigator,
            sheetShape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
        ) {
            Scaffold(
                containerColor = backgroundColor ?: Color.Transparent,
                topBar = { topBarState.value?.let { VroTopBar(state = it) } },
                bottomBar = { bottomBarState.value?.let { BottomBar(it.selectedItem) } }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = if (bottomBarState.value == null) 0.dp
                            else innerPadding.calculateBottomPadding()
                        )
                        .fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = startScreen.destinationRoute()
                    ) {
                        createComposableContent(
                            navController = navController,
                            topBarState = topBarState,
                            bottomBarState = bottomBarState
                        )
                    }
                }
            }
        }
    }

    @Composable
    open fun BottomBar(selectedItem: Int) = Unit

    fun navigateToScreen(screen: VROScreen<*, *>) {
        navController.navigate(screen.destinationRoute()) {
            popUpTo(navController.graph.id) { inclusive = true }
            launchSingleTop = true
        }
    }

    abstract fun NavGraphBuilder.createComposableContent(
        navController: NavHostController,
        topBarState: MutableState<VROTopBarState?>,
        bottomBarState: MutableState<VROBottomBarState?>,
    )
}