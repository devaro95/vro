package com.vro.atomic

import platform.Foundation.NSLock

actual class AtomicBoolean actual constructor(private val initialValue: Boolean) {
    private var value = initialValue
    private val lock = NSLock()

    actual fun get(): Boolean = lock.synchronized { value }

    actual fun set(value: Boolean) = lock.synchronized { this.value = value }

    actual fun compareAndSet(expected: Boolean, new: Boolean): Boolean =
        lock.synchronized {
            if (value == expected) {
                value = new
                true
            } else {
                false
            }
        }
}

// Helper para NSLock
internal inline fun <T> NSLock.synchronized(block: () -> T): T {
    lock()
    try {
        return block()
    } finally {
        unlock()
    }
}