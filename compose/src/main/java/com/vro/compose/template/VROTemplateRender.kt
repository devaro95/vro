package com.vro.compose.template

import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.navigation.VROBackResult
import com.vro.state.VROState
import org.koin.core.component.KoinComponent

interface VROTemplateRender<E : VROEvent, S: VROState>: KoinComponent {

    val state: S

    val events: VROEventLauncher<E>

    fun event(event: E) {
        events.doEvent(event)
    }

    fun doBack(result: VROBackResult? = null){
        events.doBack(result)
    }
}