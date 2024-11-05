package com.vro.compose.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.VROSkeleton
import com.vro.dialog.VRODialogListener
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.state.VRODialogData
import com.vro.state.VROState

abstract class VROComposableDialogContentBasics<S : VROState, E : VROEvent> {

    open val skeleton: VROSkeleton? = null

    lateinit var context: Context

    var dialogListener: VRODialogListener? = null

    lateinit var events: VROEventLauncher<E>

    @Composable
    internal open fun CreateDialog(
        state: S,
        events: VROEventLauncher<E>,
        listener: VRODialogListener?,
        onDismiss: () -> Unit,
    ) {

        this.events = events
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

    @SuppressLint("ComposableNaming")
    @Composable
    open fun onDialog(data: VRODialogData) = Unit

    @SuppressLint("ComposableNaming")
    @Composable
    open fun onError(error: Throwable, data: Any?) = Unit

    fun event(event: E) {
        events.doEvent(event)
    }
}