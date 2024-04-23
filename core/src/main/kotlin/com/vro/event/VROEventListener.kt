package com.vro.event

import com.vro.navigation.VROBackResult

interface VROEventListener<E : VROEvent> {
    fun eventListener(event: E)
    fun eventBack(result: VROBackResult? = null)
}