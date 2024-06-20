package com.vro.state

sealed class VROStepper<S> {
    data class VROStateStep<S>(val state: S) : VROStepper<S>()
    data class VRODialogStep<S>(val state: S, val dialogState: VRODialogState) : VROStepper<S>()
}