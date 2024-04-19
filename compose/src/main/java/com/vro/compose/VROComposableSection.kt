package com.vro.compose

import androidx.compose.runtime.Composable
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.event.VROEvent
import com.vro.state.VROState

abstract class VROComposableSection<S : VROState, E : VROEvent> {

    open lateinit var eventLauncher: E

    @Composable
    abstract fun CreateSection(state: S)

    @VROLightMultiDevicePreview
    @Composable
    abstract fun CreatePreview()
}