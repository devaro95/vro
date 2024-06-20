package com.vro.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.navigation.VROBackResult
import com.vro.state.VROState

abstract class VROSection<S : VROState, E : VROEvent> {

    private lateinit var eventListener: VROEventListener<E>

    @Composable
    internal fun CreateSection(state: S, eventListener: VROEventListener<E>) {
        this.eventListener = eventListener
        Column {
            SectionContent(state = state)
        }
    }

    @Composable
    abstract fun SectionContent(state: S)

    @VROLightMultiDevicePreview
    @Composable
    abstract fun SectionPreview()

    fun event(event: E) {
        eventListener.eventListener(event)
    }

    fun navigateBack(result: VROBackResult?) {
        eventListener.eventBack(result)
    }
}