package com.vro.fragment.compose

interface VROEventDispatcher<E : VROEvent> {
    fun eventListener(action: E)
}