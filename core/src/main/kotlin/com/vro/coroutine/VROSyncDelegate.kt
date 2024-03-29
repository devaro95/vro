package com.vro.coroutine

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class VROSyncDelegate<T>(defaultValue: T) : ReadWriteProperty<Any, T> {

    private var backingField = defaultValue

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return synchronized(this) {
            backingField
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        synchronized(this) {
            backingField = value
        }
    }
}