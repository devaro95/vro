package com.vro.compose

import androidx.compose.runtime.Composable
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.navigation.VROBackResult
import com.vro.state.VROState

abstract class VROSection<S : VROState, E : VROEvent> {

    internal lateinit var eventListener: VROEventListener<E>

    @Composable
    abstract fun CreateSection(state: S)

    @VROLightMultiDevicePreview
    @Composable
    abstract fun CreatePreview()

    fun event(event: E) {
        eventListener.eventListener(event)
    }

    fun navigateBack(result: VROBackResult?) {
        eventListener.eventBack(result)
    }
}