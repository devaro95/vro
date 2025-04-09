package com.vro.compose

import com.vro.core_android.viewmodel.VROViewModelNav
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper.VROSkeletonStep

/**
 * Abstract base ViewModel class for Compose-based screens.
 * Provides common functionality for navigation and state management in Jetpack Compose.
 *
 * @param S The state type that extends [VROState]
 * @param D The navigation destination type that extends [VRODestination]
 * @param E The event type that extends [VROEvent]
 */
abstract class VROComposableViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROViewModelNav<S, D, E>() {

    /**
     * Handles system back button presses.
     * By default, performs a back navigation with no result.
     * Can be overridden to provide custom back behavior.
     */
    open fun onBackSystem() {
        doBack(null)
    }

    /**
     * Shows a skeleton loading state for the screen.
     * Emits a [VROSkeletonStep] to the observable stepper with the current screen state.
     */
    fun showSkeleton() {
        observableStepper.tryEmit(VROSkeletonStep(screenState))
    }
}