package com.vro.compose.lifecycleevent

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

fun createLifecycleEventObserver(
    onCreate: () -> Unit,
    onStart: () -> Unit,
    onResume: () -> Unit,
    onPause: () -> Unit,
) = LifecycleEventObserver { _, event ->
    when (event) {
        Lifecycle.Event.ON_CREATE -> onCreate.invoke()
        Lifecycle.Event.ON_START -> onStart.invoke()
        Lifecycle.Event.ON_RESUME -> onResume.invoke()
        Lifecycle.Event.ON_PAUSE -> onPause.invoke()
        else -> Unit
    }
}