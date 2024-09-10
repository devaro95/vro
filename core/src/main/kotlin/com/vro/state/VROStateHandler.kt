package com.vro.state

import com.vro.constants.INT_ONE
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

fun <S> createStateSharedFlow() = MutableSharedFlow<S>(
    replay = INT_ONE,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

fun <D : VRODestination> createNavigationSharedFlow() = MutableSharedFlow<VRONavigationState<D>?>(
    replay = INT_ONE,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

fun <S> createStepperSharedFlow() = MutableSharedFlow<VROStepper<S>>(
    replay = INT_ONE,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

fun <S> createOneTimeSharedFlow() = MutableSharedFlow<VROOneTimeState<S>>(
    replay = INT_ONE,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)