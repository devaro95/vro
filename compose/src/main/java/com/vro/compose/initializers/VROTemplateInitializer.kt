package com.vro.compose.initializers

import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vro.compose.template.*
import com.vro.core_android.lifecycleevent.createLifecycleEventObserver
import com.vro.core_android.navigation.VRONavigator
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <VM : VROTemplateViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> InitializeStepperListener(
    viewModel: VM,
    content: VROTemplate<VM, S, D, E, M, R>,
    lifecycle: Lifecycle,
) {
    val stepper = viewModel.stepper.collectAsStateWithLifecycle(
        initialValue =
            content.skeleton?.let {
                VROStepper.VROSkeletonStep(viewModel.initialState)
            } ?: VROStepper.VROStateStep(viewModel.initialState),
        lifecycle = lifecycle
    ).value
    if (stepper is VROStepper.VROSkeletonStep) {
        content.TemplateScreenSkeleton()
    } else {
        content.TemplateContent(stepper.state)
        (stepper as? VROStepper.VRODialogStep)?.let {
            content.onDialog(it.dialogState)
        }
        (stepper as? VROStepper.VROErrorStep)?.let {
            content.onError(it.error, it.data)
        }
    }
}

@Composable
fun <VM : VROTemplateViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> InitializeOneTimeListener(
    viewModel: VM,
    content: VROTemplate<VM, S, D, E, M, R>,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getOneTimeEvents().collectLatest { oneTime ->
            if (oneTime is VROOneTimeState.Launch) {
                content.oneTimeHandler(oneTime.id, oneTime.state)
                viewModel.clearOneTime()
            }
        }
    }
}

@Composable
fun <VM : VROTemplateViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeLifecycleObserver(
    viewModel: VM,
    lifecycle: Lifecycle,
) {
    DisposableEffect(lifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = {},
            onStart = { viewModel.onStart() },
            onResume = { viewModel.onResume() },
            onPause = { viewModel.onPause() }
        )
        lifecycle.addObserver(observer)
        onDispose {
            viewModel.onPause()
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun <VM : VROTemplateViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeNavigatorListener(
    viewModel: VM,
    navigator: VRONavigator<D>,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getNavigationState().collect {
            it?.destination?.let { destination ->
                if (!destination.isNavigated) {
                    navigator.navigate(destination)
                    destination.setNavigated()
                }
            } ?: navigator.navigateBack(it?.backResult)
        }
    }
}