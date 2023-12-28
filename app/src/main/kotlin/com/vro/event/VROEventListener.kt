package com.vro.event

interface VROEventListener<E : VROEvent> {
    fun eventListener(event: E)
}