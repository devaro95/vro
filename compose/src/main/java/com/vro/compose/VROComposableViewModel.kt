package com.vro.compose

import com.vro.core_android.viewmodel.VROViewModelBasics
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.state.VROStepper.VROSkeletonStep

abstract class VROComposableViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROViewModelBasics<S, D, E>(),
    VROEventListener<E> {

    open fun onBackSystem() {
        navigateBack(null)
    }

    fun showSkeleton() {
        observableStepper.tryEmit(VROSkeletonStep(screenState))
    }
}