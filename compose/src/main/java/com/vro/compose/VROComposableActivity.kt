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
import com.vro.compose.screen.VROScreenBase
import com.vro.compose.snackbar.VROSnackbar
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState
import com.vro.compose.template.VROTemplate
import com.vro.compose.theme.*
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
     * Optional Material-based theme for the application.
     *
     * If this value is not null, [CreateMaterialTheme] will be used to wrap the app's content.
     * If both [materialTheme] and [customTheme] are null, the app falls back to a default
     * initialization without theming.
     */
    open val materialTheme: VROComposableMaterialTheme? = null

    /**
     * Optional custom theme for the application.
     *
     * If this value is not null, [CreateCustomTheme] will be used to wrap the app's content.
     * This allows defining light/dark color schemes and other design tokens independently
     * of MaterialTheme.
     *
     * If both [customTheme] and [materialTheme] are null, the app falls back to a default
     * initialization without theming.
     */
    open val customTheme: VROComposableCustomTheme? = null

    /**
     * Defines the first screen to be shown when the app starts.
     */
    abstract val startScreen: VROScreenBase<*, *>

    /**
     * Navigation controller used to manage app navigation.
     *
     * This [NavController] is responsible for handling the navigation graph,
     * managing the back stack, and enabling transitions between different
     * composable destinations within the app.
     *
     * It is marked as `lateinit` since it is initialized during setup (e.g.,
     * when creating the navigation host) and must be assigned before being used.
     */
    private lateinit var navController: NavController

    /**
     * Applies the app theme using Material3 theming with support for light and dark modes.
     *
     * The function selects the appropriate color scheme (light or dark) from the provided
     * [VROComposableMaterialTheme]. If a color scheme or typography is not supplied, it falls
     * back to the current values from [MaterialTheme].
     *
     * Additionally, it initializes the composition with the selected background color.
     *
     * @param theme The theme definition containing optional light/dark color schemes
     *              and typography to override the defaults.
     */
    @Composable
    private fun CreateMaterialTheme(theme: VROComposableMaterialTheme) {
        val colorScheme = if (isSystemInDarkTheme()) theme.darkColors else theme.lightColors
        MaterialTheme(
            colorScheme = colorScheme ?: MaterialTheme.colorScheme,
            typography = theme.typography ?: MaterialTheme.typography
        ) {
            CompositionLocalProvider(content = { Initialize(MaterialTheme.colorScheme.background) })
        }
    }

    /**
     * Applies the custom app theme by selecting between light and dark color schemes.
     *
     * The function resolves the active color scheme based on the system's dark mode state
     * using the values provided in [VROComposableCustomTheme]. If a dark scheme is not
     * defined, it falls back to the light scheme.
     *
     * The selected colors are exposed through [LocalCustomColors] so that any composable
     * can access them via CompositionLocals.
     *
     * Additionally, this function initializes the composition with the background color
     * of the chosen scheme.
     *
     * @param theme The theme definition containing light and dark color schemes,
     *              as well as optional typography.
     */
    @Composable
    open fun CreateCustomTheme(theme: VROComposableCustomTheme) {
        val background = if (isSystemInDarkTheme()) theme.darkColors?.background else theme.lightColors.background
        CompositionLocalProvider(
            *getProvidedValues().toTypedArray(),
            content = { Initialize(background) }
        )
    }

    /**
     * Provides a list of additional [ProvidedValue]s to be injected into the theme's
     * [CompositionLocalProvider].
     *
     * This function is designed to be overridden by subclasses that want to extend
     * the default theme with extra CompositionLocals, such as custom typography,
     * shapes, spacings, or any other design tokens.
     *
     * By default, it returns an empty list, meaning no extra values are provided.
     *
     * @return A list of [ProvidedValue]s to be included in the theme scope.
     */
    @Composable
    open fun getProvidedValues(): List<ProvidedValue<*>> = emptyList()

    /**
     * Entry point of the activity where the app theme is applied.
     *
     * The content is wrapped based on the available theme configuration:
     * - If [customTheme] is provided, [CreateCustomTheme] is applied.
     * - Otherwise, if [materialTheme] is provided, [CreateMaterialTheme] is applied.
     * - If neither is available, [Initialize] is called directly with no theme wrapper.
     *
     * This ensures that the UI always has a valid setup, either with a custom theme,
     * a Material3-based theme, or a bare initialization fallback.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            customTheme?.also { CreateCustomTheme(it) }
                ?: materialTheme?.let { CreateMaterialTheme(it) }
                ?: Initialize()
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
                            Row(modifier = Modifier.navigationBarsPadding()) {
                                BottomBar(selectedItem = it.selectedItem)
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
     * Navigates to the given [template], optionally attaching a [starter] object.
     *
     * @param screen The destination screen.
     * @param starter Optional navigation starter params.
     */
    fun navigateToTemplate(
        template: VROTemplate<*, *, *, *>,
        starter: VRONavStarter? = null,
    ) {
        navController.navigate(template.destinationRoute()) {
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
