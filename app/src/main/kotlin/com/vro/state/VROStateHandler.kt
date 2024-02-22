package com.vro.state

import com.vro.constants.INT_ONE
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class VroNavigationSharedFlow<D : VRODestination> {
    fun create() =
        MutableSharedFlow<VRONavigationState<D>?>(
            replay = INT_ONE,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
}

class VroStepperSharedFlow<S : VROState> {
    fun create() =
        MutableSharedFlow<VROStepper<S>>(
            replay = INT_ONE,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
}
