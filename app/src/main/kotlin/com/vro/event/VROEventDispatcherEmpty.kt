package com.vro.event

class MBEventListenerEmpty<A : VROEvent> : VROEventListener<A> {
    override fun eventListener(event: A) = Unit
}

fun <A : VROEvent> createEmptyEventListener() = MBEventListenerEmpty<A>()