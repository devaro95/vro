package com.vro.event

import com.vro.navigation.VROBackResult
import com.vro.state.createEventSharedFlow
import kotlinx.coroutines.flow.MutableSharedFlow

class VROEmptyEventLauncher<E : VROEvent> : VROEventLauncher<E> {
    override val eventObservable: MutableSharedFlow<E> = createEventSharedFlow()

    override fun doBack(result: VROBackResult?) = Unit
}