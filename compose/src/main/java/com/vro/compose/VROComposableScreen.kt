package com.vro.compose

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.vro.compose.components.VroTopBar
import com.vro.compose.extensions.getNavParamState
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.compose.states.VROComposableScaffoldState.VROTopBarState
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator
import com.vro.state.VRODialogState
import com.vro.state.VROState
import com.vro.state.VROStepper.VRODialogStep
import com.vro.state.VROStepper.VROScreenStep
import java.io.Serializable

abstract class VROComposableScreen<S : VROState, D : VRODestination, E : VROEvent> {

    internal lateinit var viewModel: VROComposableViewModel<S, D>

    open val skeletonEnabled = true

    lateinit var context: Context

    lateinit var eventLauncher: E

    @Composable
    fun TopBarState(toolbarState: VROTopBarState) {
        VroTopBar(state = toolbarState)
    }

    @Composable
    fun CreateScreen(
        viewModel: VROComposableViewModel<S, D>,
        navController: NavController,
        scaffoldState: MutableState<VROComposableScaffoldState>,
        navigator: VROComposableNavigator<D>,
        bottomBar: Boolean,
    ) {
        this.viewModel = viewModel
        eventLauncher = viewModel as E
        context = LocalContext.current
        val backResult = navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Serializable>(VROFragmentNavigator.NAVIGATION_BACK_STATE)
        val screenLifecycle = LocalLifecycleOwner.current.lifecycle
        DisposableEffect(screenLifecycle) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Event.ON_CREATE -> {
                        viewModel.isLoaded()
                        viewModel.onNavParam(getNavParamState(navController.currentDestination?.route.toString()))
                    }

                    Event.ON_START -> {
                        configureScaffold(scaffoldState, bottomBar)
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
        val stepper = viewModel.stepper.collectAsState(VROScreenStep(viewModel.initialState)).value
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
        val isLoaded by viewModel.screenLoaded

        if (!isLoaded && skeletonEnabled) AddComposableSkeleton()
        else {
            when (stepper) {
                is VROScreenStep -> AddComposableContent(stepper.state)
                is VRODialogStep -> AddComposableDialog(stepper.dialogState)
            }
        }
    }

    @Composable
    internal abstract fun AddComposableContent(state: S)

    @Composable
    internal abstract fun AddComposableDialog(dialogState: VRODialogState)

    @Composable
    internal abstract fun AddComposableSkeleton()

    open fun setTopBar(): VROTopBarState? = null

    private fun configureScaffold(
        scaffoldState: MutableState<VROComposableScaffoldState>,
        bottomBar: Boolean,
    ) {
        scaffoldState.value = VROComposableScaffoldState(
            topBarState = setTopBar(),
            showBottomBar = bottomBar
        )
    }
}
