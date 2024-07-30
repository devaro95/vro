package com.vro.compose.screen

import android.content.Context
import androidx.compose.runtime.MutableState
import com.vro.compose.states.VROBottomBarState
import com.vro.compose.states.VROTopBarState
import com.vro.event.VROEvent
import com.vro.state.VROState

abstract class VROScreen<S : VROState, E : VROEvent> : VROScreenBuilder<S, E>() {

    lateinit var context: Context

    open fun setTopBar(): VROTopBarState? = null

    open fun setBottomBar(): VROBottomBarState? = null

    internal fun configureScaffold(
        topBarState: MutableState<VROTopBarState?>,
        bottomBarState: MutableState<VROBottomBarState?>,
    ) {
        topBarState.value = setTopBar()
        bottomBarState.value = setBottomBar()
    }
}
