package com.vro.atomic

actual class AtomicBoolean actual constructor(private val initialValue: Boolean) {
    private var value = initialValue

    actual fun get(): Boolean = synchronized(this) { value }

    actual fun set(value: Boolean) = synchronized(this) { this.value = value }

    actual fun compareAndSet(expected: Boolean, new: Boolean): Boolean =
        synchronized(this) {
            if (value == expected) {
                value = new
                true
            } else {
                false
            }
        }
}