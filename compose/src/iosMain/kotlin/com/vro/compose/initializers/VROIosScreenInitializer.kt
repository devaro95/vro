package com.vro.compose.initializers

import androidx.compose.runtime.*
import com.vro.compose.screen.VROScreen
import com.vro.compose.skeleton.VROSkeletonDefault
import com.vro.compose.navigator.VROIosComposableNavigator
import com.vro.core_android.navigation.VRONavigator
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.*
import com.vro.viewmodel.VROViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * iOS equivalent of the Android screen initializer.
 * Collects stepper state and drives the VROScreen content lifecycle.
 */
@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeIosStepperListener(
    viewModel: VM,
    content: VROScreen<S, E>,
) {
    val stepper by viewModel.stepper.collectAsState(
        initial = if (content.skeleton::class != VROSkeletonDefault::class) {
            VROStepper.VROSkeletonStep(viewModel.initialState)
        } else {
            VROStepper.VROStateStep(viewModel.initialState)
        }
    )

    if (stepper is VROStepper.VROSkeletonStep) {
        content.skeleton.SkeletonContent()
    } else {
        content.ComposableScreenContainer(state = stepper.state, events = viewModel)
        (stepper as? VROStepper.VRODialogStep)?.let {
            content.dialogHandler.OnDialog(it.dialogState)
        }
        (stepper as? VROStepper.VROErrorStep)?.let {
            content.errorHandler.OnError(it.error, it.data)
        }
    }
}

@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeIosNavigatorListener(
    viewModel: VM,
    navigator: VRONavigator<D>,
) {
    LaunchedEffect(Unit) {
        viewModel.getNavigationState().collect {
            it?.destination?.let { destination ->
                if (!destination.isNavigated) {
                    navigator.onDestination(destination)
                    destination.setNavigated()
                }
            } ?: navigator.navigateBack(it?.backResult)
        }
    }
}

@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeIosOneTimeListener(
    viewModel: VM,
    content: VROScreen<S, E>,
) {
    LaunchedEffect(Unit) {
        viewModel.getOneTimeEvents().collectLatest { oneTime ->
            if (oneTime is VROOneTimeState.Launch) {
                content.oneTimeHandler.onOneTime(oneTime.id, oneTime.state)
                viewModel.clearOneTime()
            }
        }
    }
}

@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeIosLifecycle(
    viewModel: VM,
    starter: com.vro.navstarter.VRONavStarter? = null,
) {
    LaunchedEffect(Unit) {
        viewModel.onStarter(starter)
        viewModel.onStart()
        viewModel.onResume()
    }
    DisposableEffect(Unit) {
        onDispose { viewModel.onPause() }
    }
}
