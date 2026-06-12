package com.vro.compose.template

import android.content.Context

/**
 * Android-specific extension: exposes Context on VROTemplateContent.
 */
private val _contextMap = HashMap<String, Context>()

var <S : com.vro.state.VROState, E : com.vro.event.VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> VROTemplateContent<S, E, M, R>.context: Context
    get() = _contextMap[this::class.simpleName] ?: error("Context not set for ${this::class.simpleName}")
    set(value) { _contextMap[this::class.simpleName!!] = value }
