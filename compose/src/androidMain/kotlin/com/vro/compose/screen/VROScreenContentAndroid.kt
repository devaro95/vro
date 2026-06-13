package com.vro.compose.screen

import android.content.Context
import com.vro.event.VROEvent
import com.vro.state.VROState

private val _contextMap = HashMap<String, Context>()

fun <S : VROState, E : VROEvent> VROScreenContent<S, E>.setAndroidContext(context: Context) {
    _contextMap[this::class.simpleName!!] = context
}

val <S : VROState, E : VROEvent> VROScreenContent<S, E>.androidContext: Context
    get() = _contextMap[this::class.simpleName] ?: error("Context not set for ${this::class.simpleName}")
