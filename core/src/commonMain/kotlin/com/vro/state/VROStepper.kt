package com.vro.state

sealed class VROStepper<S>(
    open val state:S
) {
    data class VROStateStep<S>(
        override val state: S,
    ) : VROStepper<S>(state)

    data class VROSkeletonStep<S>(
        override val state: S,
    ) : VROStepper<S>(state)

    data class VRODialogStep<S>(
        override val state: S,
        val dialogState: VRODialogData,
    ) : VROStepper<S>(state)

    data class VROErrorStep<S>(
        override val state: S,
        val error: Throwable,
        val data: Any?,
    ) : VROStepper<S>(state)
}