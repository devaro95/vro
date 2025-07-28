package com.vro.delegate

import platform.Foundation.NSLock
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

actual class VROSyncDelegate<T> actual constructor(
    defaultValue: T
) : ReadWriteProperty<Any, T> {
    private var backingField = defaultValue
    private val lock = NSLock()

    actual override fun getValue(thisRef: Any, property: KProperty<*>): T {
        lock.lock()
        try {
            return backingField
        } finally {
            lock.unlock()
        }
    }

    actual override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        lock.lock()
        try {
            backingField = value
        } finally {
            lock.unlock()
        }
    }
}