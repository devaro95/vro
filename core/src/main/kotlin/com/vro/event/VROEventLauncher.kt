package com.vro.event

import com.vro.navigation.VROBackResult
import kotlinx.coroutines.flow.MutableSharedFlow

interface VROEventLauncher<E : VROEvent> {

    val eventObservable: MutableSharedFlow<E>

    fun doEvent(event: E) {
        eventObservable.tryEmit(event)
    }

    fun doBack(result: VROBackResult? = null)
}