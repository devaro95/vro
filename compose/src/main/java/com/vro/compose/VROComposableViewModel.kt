package com.vro.compose

import com.vro.core_android.viewmodel.VROViewModelNav
import com.vro.event.*
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper.VROSkeletonStep

abstract class VROComposableViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROViewModelNav<S, D, E>() {

    open fun onBackSystem() {
        doBack(null)
    }

    fun showSkeleton() {
        observableStepper.tryEmit(VROSkeletonStep(screenState))
    }
}