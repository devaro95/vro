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
        Event.ON_CREATE -> onCreate()
        Event.ON_START -> onStart()
        Event.ON_RESUME -> onResume()
        Event.ON_PAUSE -> onPause()
        Event.ON_DESTROY -> onDestroy()
        else -> Unit
    }
}