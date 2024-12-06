package com.vro.state

import kotlin.reflect.KProperty

class VroStateDelegate<T>(private val initialStateCreation: () -> T) {

    private var state: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return state ?: initialStateCreation().apply {
            state = this
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        state = value
    }
}