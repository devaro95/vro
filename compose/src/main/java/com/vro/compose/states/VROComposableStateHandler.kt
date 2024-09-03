package com.vro.compose.states

import com.vro.constants.INT_ONE
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

fun createTopBarSharedFlow() = MutableSharedFlow<VROTopBarLaunchState>(
    replay = INT_ONE,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)

fun createBottomBarSharedFlow() = MutableSharedFlow<VROBottomBarLaunchState>(
    replay = INT_ONE,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)