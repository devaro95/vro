package com.vro.compose.template

import android.content.Context
import com.vro.event.VROEvent
import com.vro.state.VROState

private val _templateContextMap = HashMap<String, Context>()

var <S : VROState, E : VROEvent, M : VROTemplateMapper, R : VROTemplateRender<E, S>> VROTemplateContent<S, E, M, R>.context: Context
    get() = _templateContextMap[this::class.simpleName] ?: error("Context not set for ${this::class.simpleName}")
    set(value) { _templateContextMap[this::class.simpleName!!] = value }
