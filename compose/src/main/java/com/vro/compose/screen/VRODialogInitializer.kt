package com.vro.compose.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.vro.compose.VROComposableNavigator
import com.vro.compose.VROComposableViewModel
import com.vro.compose.extensions.getNavParamState
import com.vro.compose.lifecycleevent.createLifecycleEventObserver
import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import java.io.Serializable

@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeLifecycleObserver(
    viewModel: VM,
    screenLifecycle: Lifecycle,
    navController: NavController,
) {
    DisposableEffect(screenLifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = { viewModel.onCreate(getNavParamState(navController.currentDestination?.route.toString())) },
            onStart = { viewModel.startViewModel() },
            onResume = {
                val savedBackState = navController.currentBackStackEntry?.savedStateHandle
                savedBackState?.getLiveData<Serializable>(VROComposableNavigator.NAVIGATION_BACK_STATE)?.value?.let {
                    viewModel.onNavResult(it as VROBackResult)
                    savedBackState.remove<Serializable>(VROComposableNavigator.NAVIGATION_BACK_STATE)
                }
                viewModel.onResume()
            },
            onPause = { viewModel.onPause() }
        )
        screenLifecycle.addObserver(observer)
        onDispose {
            screenLifecycle.removeObserver(observer)
        }
    }
}