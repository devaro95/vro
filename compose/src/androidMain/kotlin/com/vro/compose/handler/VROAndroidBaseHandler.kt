package com.vro.compose.handler

import android.content.Context
import com.vro.compose.screen.AndroidContextAware
import com.vro.event.VROEvent

/**
 * Android-specific base handler that exposes Android Context.
 * Use this instead of VROBaseHandler when the handler needs Context.
 */
open class VROAndroidBaseHandler<E : VROEvent> : VROBaseHandler<E>(), AndroidContextAware {

    override lateinit var context: Context
}
