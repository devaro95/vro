package com.vro.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// commonMain
expect class VROSyncDelegate<T>(defaultValue: T) : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T)
}