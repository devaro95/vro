package com.vro.core_android.viewmodel

import com.vro.event.VROEvent
import com.vro.navigation.*
import com.vro.state.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class VROViewModelNav<S : VROState, D : VRODestination, E : VROEvent> : VROViewModelCore<S, E>() {

    private val observableNavigation = createNavigationSharedFlow<D>()

    fun getNavigationState(): SharedFlow<VRONavigationState<D>?> = observableNavigation

    override val eventObservable: MutableSharedFlow<E> = createEventSharedFlow()

    fun navigate(destination: D?) {
        destination?.resetNavigated()
        observableNavigation.tryEmit(VRONavigationState(destination))
    }

    override fun doBack(result: VROBackResult?) {
        observableNavigation.tryEmit(VRONavigationState(backResult = result))
    }
}