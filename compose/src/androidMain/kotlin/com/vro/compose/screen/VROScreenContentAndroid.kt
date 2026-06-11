package com.vro.compose.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Android extension: exposes Context on VROScreenContent.
 */
var <S : com.vro.state.VROState, E : com.vro.event.VROEvent> VROScreenContent<S, E>.context: Context
    get() = _contextMap[this::class.simpleName] ?: error("Context not set for ${this::class.simpleName}")
    set(value) { _contextMap[this::class.simpleName!!] = value }

private val _contextMap = HashMap<String, Context>()
