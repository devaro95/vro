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
import androidx.core.view.WindowCompat
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.*
import com.vro.compose.components.VroTopBar
import com.vro.compose.extensions.destinationRoute
import com.vro.compose.screen.VROScreen
import com.vro.compose.snackbar.VROSnackbar
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState
import com.vro.navigation.putStarterParam
import com.vro.navstarter.VRONavStarter

/**
 * Base class for Jetpack Compose activities that follow the VRO architecture pattern.
 *
 * [VROComposableActivity] provides built-in support for:
 * - Applying light/dark themes
 * - Managing navigation using Jetpack Navigation and Accompanist Bottom Sheets
 * - Handling top bar, bottom bar, and snackbar state
 * - Rendering screen content using [VROScreen]
 *
 * Subclasses must define the [startScreen] and provide the [NavGraphBuilder.createComposableContent] to
 * register the navigation graph.
 */
abstract class VROComposableActivity : ComponentActivity() {

    /**
     * Optional theme for the application. If null, default MaterialTheme will be used.
     */
    open val theme: VROComposableTheme? = null

    /**
     * Defines the first screen to be shown when the app starts.
     */
    abstract val startScreen: VROScreen<*, *>

    private lateinit var navController: NavController

    /**
     * Applies the app theme using the provided light/dark color schemes and typography.
     *
     * @param lightColors Light theme color scheme.
     * @param darkColors Dark theme color scheme.
     * @param typography App typography.
     * @param content The UI content to be wrapped with the theme.
     */
    @Composable
    private fun CreateTheme(
        lightColors: ColorScheme?,
        darkColors: ColorScheme?,
        typography: Typography?,
        content: @Composable () -> Unit,
    ) {
        val colorScheme = if (isSystemInDarkTheme()) darkColors else lightColors
        MaterialTheme(
            colorScheme = colorScheme ?: MaterialTheme.colorScheme,
            typography = typography ?: MaterialTheme.typography
        ) {
            CompositionLocalProvider(content = content)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            theme?.also {
                CreateTheme(it.lightColors, it.darkColors, it.typography) {
                    Initialize(MaterialTheme.colorScheme.background)
                }
            } ?: run {
                Initialize()
            }
        }
    }

    /**
     * Initializes the composable layout, navigation controller, and UI scaffolding.
     *
     * @param backgroundColor Optional background color for the app container.
     */
    @OptIn(ExperimentalMaterialNavigationApi::class)
    @Composable
    fun Initialize(backgroundColor: Color? = null) {
        val bottomSheetNavigator = rememberBottomSheetNavigator()
        val navController = rememberNavController(bottomSheetNavigator)
        this.navController = navController

        val topBarState = remember { mutableStateOf<VROTopBarBaseState>(VROTopBarStartState()) }
        val bottomBarState = remember { mutableStateOf<VROBottomBarBaseState>(VROBottomBarStartState()) }
        val snackbarHostState = remember { SnackbarHostState() }
        val snackbarState = remember { mutableStateOf(VROSnackBarState(snackbarHostState)) }

        LaunchedEffect(navController) {
            onInitialized()
        }

        ModalBottomSheetLayout(
            modifier = Modifier
                .fillMaxSize(),
            bottomSheetNavigator = bottomSheetNavigator,
            sheetShape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
        ) {
            Scaffold(
                contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal),
                containerColor = backgroundColor ?: Color.Transparent,
                topBar = {
                    (topBarState.value as? VROTopBarBaseState.VROTopBarState)?.let {
                        if (topBarState.value.visibility) {
                            VroTopBar(state = it)
                        }
                    }
                },
                bottomBar = {
                    (bottomBarState.value as? VROBottomBarState)?.let {
                        if (bottomBarState.value.visibility) {
                            Row(modifier = Modifier.navigationBarsPadding() ) {
                                BottomBar(selectedItem = it.selectedItem,)
                            }
                        }
                    }
                },
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        snackbar = {
                            VROSnackbar(
                                data = it,
                                backgroundColor = snackbarState.value.backgroundColor,
                                textColor = snackbarState.value.textColor,
                                actionButtonColor = snackbarState.value.actionColor
                            )
                        }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = startScreen.destinationRoute()
                    ) {
                        createComposableContent(
                            navController = navController,
                            topBarState = topBarState,
                            bottomBarState = bottomBarState,
                            snackbarState = snackbarState
                        )
                    }
                }
            }
        }
    }

    open fun onInitialized() = Unit

    /**
     * Optional implementation of the bottom bar.
     * Can be overridden by subclasses to provide custom behavior or layout.
     *
     * @param selectedItem Index of the currently selected bottom bar item.
     */
    @Composable
    open fun BottomBar(selectedItem: Int) = Unit

    /**
     * Returns the route of the current screen.
     *
     * @throws Exception if no current destination is found.
     */
    fun getCurrentScreen(): String {
        return navController.currentDestination?.route
            ?: throw Exception("No current screen found")
    }

    /**
     * Navigates to the given [screen], optionally attaching a [starter] object.
     *
     * @param screen The destination screen.
     * @param starter Optional navigation starter params.
     */
    fun navigateToScreen(
        screen: VROScreen<*, *>,
        starter: VRONavStarter? = null,
    ) {
        navController.navigate(screen.destinationRoute()) {
            popUpTo(navController.graph.id) { inclusive = true }
            launchSingleTop = true
        }
        starter?.let {
            putStarterParam(navController.currentDestination?.id.toString(), it)
        }
    }

    /**
     * Abstract function to register all the composable destinations for the app.
     *
     * Must be implemented by the extending class to define the navigation graph.
     *
     * @param navController The current [NavHostController].
     * @param topBarState Mutable state object to control the top bar UI.
     * @param bottomBarState Mutable state object to control the bottom bar UI.
     * @param snackbarState Mutable state object to control the snackbar UI.
     */
    abstract fun NavGraphBuilder.createComposableContent(
        navController: NavHostController,
        topBarState: MutableState<VROTopBarBaseState>,
        bottomBarState: MutableState<VROBottomBarBaseState>,
        snackbarState: MutableState<VROSnackBarState>,
    )
}
