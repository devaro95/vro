package com.vro.compose.dialog

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.*
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableViewModel
import com.vro.compose.extensions.getNavParamState
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator
import com.vro.state.VROState
import com.vro.state.VROStepper
import java.io.Serializable

abstract class VROComposableBottomSheet<S : VROState, D : VRODestination, E : VROEvent> {

    internal lateinit var viewModel: VROComposableViewModel<S, D>

    open val skeletonEnabled = true

    lateinit var context: Context

    lateinit var eventLauncher: E

    @Composable
    fun CreateBottomSheet(
        viewModel: VROComposableViewModel<S, D>,
        navController: NavController,
        navigator: VROComposableNavigator<D>,
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
        val stepper = viewModel.stepper.collectAsState(VROStepper.VROScreenStep(viewModel.initialState)).value
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
        else if (stepper is VROStepper.VROScreenStep) AddComposableContent(stepper.state)
    }

    @Composable
    internal abstract fun AddComposableContent(state: S)

    @Composable
    internal abstract fun AddComposableSkeleton()
}