package com.vro.state

open class VROOneTimeState<S> {
    data class Launch<S>(val id: Int, val state: S) : VROOneTimeState<S>()
    data class Clear<S>(val state: S? = null) : VROOneTimeState<S>()
}
