package com.vro.compose.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.event.VROEvent
import com.vro.state.VROState

abstract class VROScreen<S : VROState, E : VROEvent> : VROScreenBuilder<S, E>() {

    lateinit var context: Context

    @Composable
    open fun ComposableSkeleton() = Unit

    open fun setTopBar(): VROComposableScaffoldState.VROTopBarState? = null

    internal fun configureScaffold(
        scaffoldState: MutableState<VROComposableScaffoldState>,
        bottomBar: Boolean,
    ) {
        scaffoldState.value = VROComposableScaffoldState(
            topBarState = setTopBar(),
            showBottomBar = bottomBar
        )
    }
}
