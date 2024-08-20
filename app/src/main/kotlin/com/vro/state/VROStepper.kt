package com.vro.state

sealed class VROStepper<S> {
    data class VROStateStep<S>(val state: S) : VROStepper<S>()
    data class VROSkeletonStep<S>(val state: S) : VROStepper<S>()
    data class VRODialogStep<S>(val state: S, val dialogState: VRODialogState) : VROStepper<S>()
    data class VROErrorStep<S>(val error: Throwable, val data: Any?) : VROStepper<S>()
}

data object VROSnackBar