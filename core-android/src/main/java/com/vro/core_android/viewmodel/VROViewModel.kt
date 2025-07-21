package com.vro.core_android.viewmodel

import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper

abstract class VROViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROViewModelNav<S, D, E>(){

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
     * Emits a [com.vro.state.VROStepper.VROSkeletonStep] to the observable stepper with the current screen state.
     */
    fun showSkeleton() {
        observableStepper.tryEmit(VROStepper.VROSkeletonStep(screenState))
    }
}