package com.vro.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

actual class VROSyncDelegate<T> actual constructor(
    defaultValue: T
) : ReadWriteProperty<Any, T> {
    private var backingField = defaultValue
    private val lock = Any()

    actual override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return synchronized(lock) { backingField }
    }

    actual override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        synchronized(lock) { backingField = value }
    }
}