package com.vro.compose.template

import androidx.compose.runtime.Composable
import com.vro.event.VROEvent
import com.vro.state.VROState

abstract class VROTemplateRender<E : VROEvent, S : VROState> {

    lateinit var events: (E) -> Unit

    @Composable
    abstract fun Render()
}
