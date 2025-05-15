package com.vro.compose.initializers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.vro.compose.VROComposableViewModel
import com.vro.core_android.lifecycleevent.createLifecycleEventObserver
import com.vro.event.VROEvent
import com.vro.navigation.*
import com.vro.state.VROState

/**
 * Initializes and manages lifecycle observers for a Compose screen with ViewModel support.
 * Handles screen lifecycle events and coordinates with navigation parameters.
 *
 * This function:
 * - Observes onCreate/onStart/onResume/onPause events
 * - Processes starter parameters during creation
 * - Handles navigation results during resume
 * - Automatically cleans up observers when disposed
 *
 * @param VM The ViewModel type that extends [VROComposableViewModel]
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 *
 * @param viewModel The ViewModel instance to observe
 * @param screenLifecycle The lifecycle owner to observe
 * @param navController The NavController for parameter handling
 *
 * @see VROComposableViewModel For expected ViewModel interface
 * @see createLifecycleEventObserver For base observer implementation
 * @see DisposableEffect For lifecycle-aware composition
 *
 */
@Composable
fun <VM : VROComposableViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> InitializeLifecycleObserver(
    viewModel: VM,
    screenLifecycle: Lifecycle,
    navController: NavController,
) {
    DisposableEffect(screenLifecycle) {
        val observer = createLifecycleEventObserver(
            onCreate = { viewModel.onStarter(getStarterParam(navController.currentDestination?.id.toString())) },
            onStart = { viewModel.onStart() },
            onResume = {
                viewModel.getResult()
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