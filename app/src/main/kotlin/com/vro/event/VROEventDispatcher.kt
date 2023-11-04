package com.vro.event

interface VROEventDispatcher<E : VROEvent> {
    fun eventListener(action: E)
}