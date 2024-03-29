package com.vro.compose.dialog

import android.content.Context
import androidx.compose.runtime.Composable
import com.vro.compose.VROComposableViewModel
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROComposableBottomSheetContent<S : VROState, D : VRODestination, E : VROEvent> {

    internal lateinit var viewModel: VROComposableViewModel<S, D>

    open val skeletonEnabled = true

    lateinit var context: Context

    lateinit var eventLauncher: E

    @Composable
    abstract fun ComposableContent(state: S)

    @Composable
    open fun ComposableSkeleton() = Unit
}