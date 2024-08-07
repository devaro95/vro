package com.vro.compose.dialog

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.state.VRODialogState
import com.vro.state.VROState

abstract class VROComposableDialogContent<S : VROState, E : VROEvent> {

    internal lateinit var eventListener: VROEventListener<E>

    lateinit var context: Context

    @Composable
    internal fun CreateDialog(
        state: S,
        eventListener: VROEventListener<E>,
        onDismiss: () -> Unit,
        dismissOnBackPress: Boolean,
        dismissOnClickOutside: Boolean,
    ) {
        this.eventListener = eventListener
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside
            )
        ) {
            ComposableContent(state, onDismiss)
        }
    }

    @Composable
    abstract fun ComposableContent(state: S, dismiss: () -> Unit)

    @Composable
    internal fun ComposableDialogSkeleton() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ComposableSkeleton()
        }
    }

    @Composable
    open fun ComposableSkeleton() = Unit

    @Composable
    open fun OnDialog(data: VRODialogState) = Unit

    @Composable
    open fun OnError(error: Throwable, data: Any?) = Unit

    fun event(event: E) {
        eventListener.eventListener(event)
    }
}