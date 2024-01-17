package com.vro.navigation

open class VRODestination {
    var isNavigated: Boolean = false
        private set

    internal fun setNavigated() {
        isNavigated = true
    }

    internal fun resetNavigated() {
        isNavigated = false
    }
}