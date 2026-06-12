package com.vro.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
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
import com.vro.compose.composition.*
import com.vro.compose.extensions.destinationRoute
import com.vro.compose.screen.VROScreen
import com.vro.compose.screen.VROScreenBase
import com.vro.compose.snackbar.VROSnackbar
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState
import com.vro.compose.theme.VROComposableCustomTheme
import com.vro.compose.theme.VROComposableMaterialTheme
import com.vro.navigation.putStarterParam
import com.vro.navstarter.VRONavStarter
import kotlin.reflect.KClass

abstract class VROComposableActivity : ComponentActivity() {

    open val materialTheme: VROComposableMaterialTheme? = null
    open val customTheme: VROComposableCustomTheme? = null
    abstract val startScreen: KClass<out VROScreenBase<*, *>>
    private lateinit var navController: NavController

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

    @Composable
    open fun CreateCustomTheme(theme: VROComposableCustomTheme) {
        val background = if (isSystemInDarkTheme()) theme.darkColors?.background else theme.lightColors.background
        CompositionLocalProvider(
            *getProvidedValues().toTypedArray(),
            content = { Initialize(background) }
        )
    }

    @Composable
    open fun getProvidedValues(): List<ProvidedValue<*>> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            customTheme?.also { CreateCustomTheme(it) }
                ?: materialTheme?.let { CreateMaterialTheme(it) }
                ?: Initialize()
        }
    }

    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalSharedTransitionApi::class)
    @Composable
    fun Initialize(backgroundColor: Color? = null) {
        val bottomSheetNavigator = rememberBottomSheetNavigator()
        val navController = rememberNavController(bottomSheetNavigator)
        this.navController = navController
        val topBarState = remember { mutableStateOf<VROTopBarBaseState>(VROTopBarStartState()) }
        val bottomBarState = remember { mutableStateOf<VROBottomBarBaseState>(VROBottomBarStartState()) }
        val snackBarHostState = remember { SnackbarHostState() }
        val snackBarState = remember { mutableStateOf(VROSnackBarState(snackBarHostState)) }

        LaunchedEffect(navController) { onInitialized() }

        ModalBottomSheetLayout(
            modifier = Modifier.fillMaxSize(),
            bottomSheetNavigator = bottomSheetNavigator,
            sheetShape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
        ) {
            Scaffold(
                contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal),
                containerColor = backgroundColor ?: Color.Transparent,
                topBar = {
                    (topBarState.value as? VROTopBarBaseState.VROTopBarState)?.let {
                        if (topBarState.value.visibility) VroTopBar(state = it)
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
                        hostState = snackBarHostState,
                        snackbar = {
                            VROSnackbar(
                                data = it,
                                backgroundColor = snackBarState.value.backgroundColor,
                                textColor = snackBarState.value.textColor,
                                actionButtonColor = snackBarState.value.actionColor
                            )
                        }
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                ) {
                    SharedTransitionLayout {
                        CompositionLocalProvider(
                            LocalSharedTransitionScope provides this,
                            LocalTopBarState provides topBarState,
                            LocalBottomBarState provides bottomBarState,
                            LocalSnackbarState provides snackBarState
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = startScreen.destinationRoute()
                            ) {
                                createComposableContent(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }

    open fun onInitialized() = Unit

    fun hideKeyboard() {
        currentFocus?.clearFocus()
    }

    @Composable
    open fun BottomBar(selectedItem: Int) = Unit

    fun getCurrentScreen(): String {
        return navController.currentDestination?.route ?: throw Exception("No current screen found")
    }

    fun navigate(destination: KClass<out Any>, starter: VRONavStarter? = null) {
        navController.navigate(destination.destinationRoute()) {
            popUpTo(navController.graph.id) { inclusive = true }
            launchSingleTop = true
        }
        starter?.let { putStarterParam(navController.currentDestination?.id.toString(), it) }
    }

    abstract fun NavGraphBuilder.createComposableContent(navController: NavHostController)
}
