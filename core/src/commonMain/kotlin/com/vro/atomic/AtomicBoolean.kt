package com.vro.atomic

expect class AtomicBoolean(initialValue: Boolean) {
    fun get(): Boolean
    fun set(value: Boolean)
    fun compareAndSet(expected: Boolean, new: Boolean): Boolean
}