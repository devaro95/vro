package com.vro.compose

import android.os.Bundle
import android.view.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.accompanist.navigation.material.*
import com.vro.compose.components.VroTopBar
import com.vro.compose.extensions.VROComposableFragmentScreen
import com.vro.compose.screen.VROScreen
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState
import com.vro.core_android.fragment.VROFragmentInjection
import com.vro.core_android.navigation.VROFragmentNavigator
import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.state.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Abstract base class for fragments that use Jetpack Compose for their UI.
 * Provides a structured way to handle navigation, theming, and common UI components.
 *
 * @param VM The ViewModel type that extends [VROComposableViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param SC The screen composable type that extends [VROScreen]
 * @param E The event type that extends [VROEvent]
 */
abstract class VROComposableFragment<
        VM : VROComposableViewModel<S, D, E>,
        S : VROState,
        D : VRODestination,
        SC : VROScreen<S, E>,
        E : VROEvent,
        > : VROFragmentInjection<VM>() {

    /**
     * The navigation controller for handling navigation between destinations.
     * Must be implemented by concrete fragments.
     */
    abstract val navigator: VROFragmentNavigator<D>

    /**
     * Optional theme configuration for the fragment.
     * Can be overridden to provide custom theming.
     */
    open val theme: VROComposableTheme? = null

    private lateinit var navController: NavController

    /**
     * Abstract composable function that must be implemented to provide the main content.
     * @return The root composable screen for this fragment
     */
    @Composable
    abstract fun composableView(): SC

    /**
     * Creates a MaterialTheme with the provided color schemes and typography.
     * @param lightColors Color scheme for light theme
     * @param darkColors Color scheme for dark theme
     * @param typography Typography configuration
     * @param content The composable content to be themed
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
            CompositionLocalProvider(
                content = content
            )
        }
    }

    /**
     * Called to do initial creation of the fragment.
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers(viewModel, this)
    }

    /**
     * Sets up observers for ViewModel events.
     * @param viewModel The ViewModel instance
     * @param fragment The fragment to associate observers with
     */
    private fun setObservers(viewModel: VM, fragment: Fragment) {
        fragment.lifecycleScope.launch {
            viewModel.stepper.collectLatest { stepper ->
                when (stepper) {
                    is VROStepper.VRODialogStep -> onLoadDialog(stepper.dialogState)
                    is VROStepper.VROErrorStep -> onError(stepper.error)
                    else -> Unit
                }
            }
        }
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state
     * @return Return the View for the fragment's UI
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner))
            setContent {
                theme?.also {
                    CreateTheme(it.lightColors, it.darkColors, it.typography) {
                        Initialize(backgroundColor = MaterialTheme.colorScheme.background)
                    }
                } ?: run {
                    Initialize()
                }
            }
        }
    }

    /**
     * Initializes the Compose UI with common components like Scaffold, TopBar, BottomBar, and Snackbar.
     * @param backgroundColor Optional background color for the scaffold
     */
    @OptIn(ExperimentalMaterialNavigationApi::class)
    @Composable
    fun Initialize(
        backgroundColor: Color? = null,
    ) {
        val bottomSheetNavigator = rememberBottomSheetNavigator()
        this.navController = this@VROComposableFragment.findNavController()
        val topBarState = remember { mutableStateOf<VROTopBarBaseState>(VROTopBarStartState()) }
        val bottomBarState = remember { mutableStateOf<VROBottomBarBaseState>(VROBottomBarStartState()) }
        val snackbarHostState = remember { SnackbarHostState() }
        val snackbarState = remember { mutableStateOf<VROSnackBarState>(VROSnackBarState(snackbarHostState)) }
        ModalBottomSheetLayout(
            modifier = Modifier.fillMaxSize(),
            bottomSheetNavigator = bottomSheetNavigator,
            sheetShape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
        ) {
            Scaffold(
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
                            BottomBar(selectedItem = (it.selectedItem))
                        }
                    }
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = if (!bottomBarState.value.visibility) 0.dp
                            else innerPadding.calculateBottomPadding()
                        )
                        .fillMaxSize()
                ) {
                    VROComposableFragmentScreen(
                        viewModel = viewModel,
                        navigator = navigator,
                        topBarState = topBarState,
                        bottomBarState = bottomBarState,
                        snackbarState = snackbarState,
                        content = composableView()
                    )
                }
            }
        }
    }

    /**
     * Called when a dialog needs to be shown.
     * @param data The dialog configuration data
     */
    open fun onLoadDialog(data: VRODialogData) = Unit

    /**
     * Called when an error occurs.
     * @param error The error that occurred
     */
    open fun onError(error: Throwable) = Unit

    /**
     * Composable function for the bottom bar. Can be overridden to provide custom bottom bar implementations.
     * @param selectedItem The currently selected bottom bar item
     */
    @Composable
    open fun BottomBar(selectedItem: Int) = Unit

    /**
     * Called when the fragment is visible to the user.
     */
    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    /**
     * Sends an event to the ViewModel.
     * @param event The event to send
     */
    fun event(event: E) {
        viewModel.doEvent(event)
    }

    /**
     * Navigates back with an optional result.
     * @param result The result to pass back, or null if no result
     */
    fun navigateBack(result: VROBackResult?) {
        viewModel.doBack(result)
    }
}