package com.vro.event

interface VROEventListener<E : VROEvent> {
    fun sendEvent(event: E)
}