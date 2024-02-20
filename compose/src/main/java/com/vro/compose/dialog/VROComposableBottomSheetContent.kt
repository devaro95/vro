package com.vro.compose.dialog

import androidx.compose.runtime.Composable
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROComposableBottomSheetContent<
        S : VROState,
        D : VRODestination,
        E : VROEvent,
        > : VROComposableBottomSheet<S, D, E>() {
    @Composable
    override fun AddComposableContent(state: S) {
        ComposableContent(state)
    }

    @Composable
    override fun AddComposableSkeleton() {
        ComposableSkeleton()
    }

    @Composable
    abstract fun ComposableContent(state: S)

    @Composable
    open fun ComposableSkeleton() = Unit
}