package com.vro.viewmodel

import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import com.vro.navigation.putResultParam
import com.vro.state.VROState
import com.vro.state.createEventSharedFlow
import com.vro.state.createNavigationSharedFlow
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

    fun setResult(result: VROBackResult){
        putResultParam(result.id, result)
    }

    override fun doBack(result: VROBackResult?) {
        observableNavigation.tryEmit(VRONavigationState(backResult = result))
    }
}