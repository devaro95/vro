package com.vro.state

sealed class VROOneTimeState<S> {
    data class Launch<S>(val id: Int, val state: S) : VROOneTimeState<S>()
    data class Clear<S>(val state: S? = null) : VROOneTimeState<S>()
}