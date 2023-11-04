package com.vro.fragment.compose

class MBActionDispatcherEmpty<A : VROEvent> : VROEventDispatcher<A> {
    override fun eventListener(action: A) = Unit
}

fun <A : VROEvent> createActionDispatcherEmpty() = MBActionDispatcherEmpty<A>()