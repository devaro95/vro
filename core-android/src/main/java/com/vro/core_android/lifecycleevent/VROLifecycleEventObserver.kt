package com.vro.core_android.lifecycleevent

import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleEventObserver

fun createLifecycleEventObserver(
    onCreate: () -> Unit = {},
    onStart: () -> Unit = {},
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onDestroy: () -> Unit = {},
) = LifecycleEventObserver { _, event ->
    when (event) {
        Event.ON_CREATE -> onCreate.invoke()
        Event.ON_START -> onStart.invoke()
        Event.ON_RESUME -> onResume.invoke()
        Event.ON_PAUSE -> onPause.invoke()
        Event.ON_DESTROY -> onDestroy.invoke()
        else -> Unit
    }
}