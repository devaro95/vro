package com.vro.compose.screen

import android.content.Context
import androidx.compose.runtime.MutableState
import com.vro.compose.states.*
import com.vro.event.VROEvent
import com.vro.state.VROState
import kotlinx.coroutines.flow.Flow

abstract class VROScreen<S : VROState, E : VROEvent> : VROScreenBase<S, E>() {

    lateinit var context: Context

    internal fun configureScaffold(
        topBarState: MutableState<VROTopBarState?>,
        bottomBarState: MutableState<VROBottomBarState?>,
    ) {
        topBarState.value = setTopBar()
        bottomBarState.value = setBottomBar()
    }
}
