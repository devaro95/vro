package com.vro.compose.dialog

import android.content.Context
import androidx.compose.runtime.Composable
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.state.VROState

abstract class VROComposableBottomSheetContent<S : VROState, E : VROEvent> {

    private lateinit var eventListener: VROEventListener<E>

    open val skeletonEnabled = true

    lateinit var context: Context

    @Composable
    internal fun ComposableContainer(state: S, eventListener: VROEventListener<E>, dismiss: () -> Unit) {
        this.eventListener = eventListener
        ComposableContent(state, dismiss)
    }

    @Composable
    abstract fun ComposableContent(state: S, dismiss: () -> Unit)

    @VROLightMultiDevicePreview
    @Composable
    abstract fun ComposablePreview()

    @Composable
    open fun ComposableSkeleton() = Unit

    fun event(event: E) {
        eventListener.eventListener(event)
    }
}