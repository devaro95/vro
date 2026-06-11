package com.vro.compose.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vro.compose.handler.*
import com.vro.compose.handler.default.*
import com.vro.compose.skeleton.VROSkeleton
import com.vro.compose.skeleton.VROSkeletonDefault
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.state.VROState

/**
 * Android-specific extension of VROScreenBase that adds Context support for handlers.
 * Used by VROComposableActivity and VROComposableFragment.
 */
@Composable
internal fun <S : VROState, E : VROEvent> VROScreenBase<S, E>.InitializeHandlersAndroid(
    events: VROEventLauncher<E>
) {
    val context: Context = LocalContext.current
    this.dialogHandler.events = events
    (this.dialogHandler as? AndroidContextAware)?.context = context
    this.errorHandler.events = events
    (this.errorHandler as? AndroidContextAware)?.context = context
    this.oneTimeHandler.events = events
    (this.oneTimeHandler as? AndroidContextAware)?.context = context
}

/**
 * Marker interface for handlers that need Android Context.
 */
interface AndroidContextAware {
    var context: Context
}
