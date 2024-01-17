package com.vro.compose

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle.*
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.vro.compose.components.VroTopBar
import com.vro.compose.extensions.getNavParamState
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.compose.states.VROComposableScaffoldState.*
import com.vro.dialog.VRODialogState
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator
import com.vro.state.VROState
import java.io.Serializable

@ExperimentalMaterial3Api
abstract class VROComposableScreen<S : VROState, D : VRODestination, E : VROEvent> {

    internal lateinit var viewModel: VROComposableViewModel<S, D, E>

    open val skeletonEnabled = true

    lateinit var context: Context

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBarState(toolbarState: VROTopBarState) {
        VroTopBar(
            title = toolbarState.title,
            actionButton = toolbarState.actionButton,
            navigationButton = toolbarState.navigationButton
        )
    }

    @Composable
    fun CreateScreen(
        viewModel: VROComposableViewModel<S, D, E>,
        navController: NavController,
        scaffoldState: MutableState<VROComposableScaffoldState>,
        navigator: VROComposableNavigator<D>,
    ) {
        this.viewModel = viewModel
        context = LocalContext.current
        val backResult = navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Serializable>(VROFragmentNavigator.NAVIGATION_BACK_STATE)
        val screenLifecycle = LocalLifecycleOwner.current.lifecycle
        DisposableEffect(screenLifecycle) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Event.ON_CREATE -> {
                        viewModel.initialize()
                        viewModel.onNavParam(getNavParamState(navController.currentDestination?.route.toString()))
                    }

                    Event.ON_START -> {
                        configureScaffold(scaffoldState)
                        viewModel.startViewModel(backResult?.value)
                    }

                    Event.ON_RESUME -> {
                        viewModel.onResume()
                    }

                    Event.ON_PAUSE -> viewModel.onPause()
                    else -> Unit
                }
            }
            screenLifecycle.addObserver(observer)
            onDispose {
                screenLifecycle.removeObserver(observer)
            }
        }
        val state by viewModel.state.collectAsState(remember { viewModel.initialViewState })
        val dialogState by viewModel.dialogState.observeAsState()
        LaunchedEffect(key1 = Unit) {
            viewModel.navigationState.collect {
                it?.destination?.let { destination ->
                    if (!destination.isNavigated) {
                        navigator.navigate(destination)
                        destination.setNavigated()
                    }
                } ?: navigator.navigateBack(it?.backResult)
            }
        }
        BackHandler(true) { navigator.navigateBack(null) }
        val isLoaded by viewModel.startLoading
        if (!isLoaded && skeletonEnabled) AddComposableSkeleton()
        else AddComposableContent(state)
        dialogState?.let { AddComposableDialog(it) }
    }

    @Composable
    internal abstract fun AddComposableContent(state: S)

    @Composable
    internal abstract fun AddComposableDialog(dialogState: VRODialogState)

    @Composable
    internal abstract fun AddComposableSkeleton()

    open fun setTopBar(): VROTopBarState? = null

    open fun setBottomBar(): VROBottomBarState? = null

    private fun configureScaffold(
        scaffoldState: MutableState<VROComposableScaffoldState>,
    ) {
        scaffoldState.value = VROComposableScaffoldState(
            topBarState = setTopBar(),
            bottomBarState = setBottomBar()
        )
    }
}
