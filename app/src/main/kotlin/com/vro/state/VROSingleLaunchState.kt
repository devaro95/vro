package com.vro.state

sealed class VROSingleLaunchState<S> {
    data class Launch<S>(val id: Int, val state: S) : VROSingleLaunchState<S>()
    data class Clear<S>(val state: S? = null) : VROSingleLaunchState<S>()
}
