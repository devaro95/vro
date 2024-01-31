package com.vro.state

import com.vro.dialog.VRODialogState
import com.vro.event.VROSingleLiveEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

internal class VROStateHandler<S : VROState, D : VRODestination>(
    val screenState: Flow<S>,
    val navigationState: SharedFlow<VRONavigationState<D>?>,
    val dialogState: VROSingleLiveEvent<VRODialogState?> = VROSingleLiveEvent(),
)

internal class VroNavigationSharedFlow<D : VRODestination> {
    fun create() =
        MutableSharedFlow<VRONavigationState<D>?>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
}

internal class VroStateSharedFlow<S : VROState> {
    fun create() =
        MutableSharedFlow<S>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
}