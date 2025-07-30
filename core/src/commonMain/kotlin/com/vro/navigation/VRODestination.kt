package com.vro.navigation

open class VRODestination {
    var isNavigated: Boolean = false
        private set

    fun setNavigated() {
        isNavigated = true
    }

    fun resetNavigated() {
        isNavigated = false
    }
}