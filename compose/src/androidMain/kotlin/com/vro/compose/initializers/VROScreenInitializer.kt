package com.vro.compose.initializers

import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vro.compose.composition.LocalBottomBarState
import com.vro.compose.composition.LocalTopBarState
import com.vro.compose.screen.VROScreen
import com.vro.compose.skeleton.VROSkeletonDefault
import com.vro.core_android.lifecycleevent.createLifecycleEventObserver
import com.vro.viewmodel.VROViewModel
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.getStarterParam
import com.vro.state.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeOneTimeListener(
    viewModel: VM,
    content: VROScreen<S, E>,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getOneTimeEvents().collectLatest { oneTime ->
            if (oneTime is VROOneTimeState.Launch) {
                content.oneTimeHandler.onOneTime(oneTime.id, oneTime.state)
                viewModel.clearOneTime()
            }
        }
    }
}

@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeStepperListener(
    viewModel: VM,
    content: VROScreen<S, E>,
    screenLifecycle: Lifecycle
) {
    content.InitializeEvents(events = viewModel)
    content.InitializeBars()
    val stepper = viewModel.stepper.collectAsStateWithLifecycle(
        initialValue = if (content.skeleton::class != VROSkeletonDefault::class) {
            VROStepper.VROSkeletonStep(viewModel.initialState)
        } else {
            VROStepper.VROStateStep(viewModel.initialState)
        },
        lifecycle = screenLifecycle
    ).value

    if (stepper is VROStepper.VROSkeletonStep) {
        content.skeleton.SkeletonContent()
    } else {
        content.ComposableScreenContainer(
            state = stepper.state,
            events = viewModel
        )
        (stepper as? VROStepper.VRODialogStep)?.let {
            content.dialogHandler.OnDialog(it.dialogState)
        }
        (stepper as? VROStepper.VROErrorStep)?.let {
            content.errorHandler.OnError(it.error, it.data)
        }
    }
}

@Composable
fun <VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeLifecycleObserver(
    viewModel: VM,
    content: VROScreen<S, E>,
    screenLifecycle: Lifecycle,
    navController: NavController,
) {
    DisposableEffect(screenLifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = {
                navController.currentDestination?.id?.let { destinationId ->
                    viewModel.onStarter(getStarterParam(destinationId.toString()))
                }
            },
            onStart = { viewModel.onStart() },
            onResume = {
                viewModel.getResult()
                viewModel.onResume()
            },
            onPause = { viewModel.onPause() }
        )
        screenLifecycle.addObserver(observer)
        onDispose {
            viewModel.onPause()
            screenLifecycle.removeObserver(observer)
        }
    }
}
