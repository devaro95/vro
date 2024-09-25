package com.vro.compose.dialog

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.VROSkeleton
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.state.VRODialogData
import com.vro.state.VROState

abstract class VROComposableDialogContentBasics<S : VROState, E : VROEvent> {

    open val skeleton: VROSkeleton? = null

    internal lateinit var eventListener: VROEventListener<E>

    lateinit var context: Context

    var dialogListener: VRODialogListener? = null

    @Composable
    internal open fun CreateDialog(
        state: S,
        eventListener: VROEventListener<E>,
        listener: VRODialogListener?,
        onDismiss: () -> Unit,
    ) {
        this.eventListener = eventListener
        dialogListener = listener
    }

    @Composable
    internal fun ComposableDialogSkeleton() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            skeleton?.SkeletonContent()
        }
    }

    @Composable
    abstract fun ComposableContent(state: S, dismiss: () -> Unit)

    @VROLightMultiDevicePreview
    @Composable
    abstract fun ComposablePreview()

    @Composable
    open fun ComposableSkeleton() = Unit

    @Composable
    open fun OnDialog(data: VRODialogData) = Unit

    @Composable
    open fun OnError(error: Throwable, data: Any?) = Unit

    fun event(event: E) {
        eventListener.eventListener(event)
    }
}