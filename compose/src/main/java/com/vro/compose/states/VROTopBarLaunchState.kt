package com.vro.compose.states

sealed class VROTopBarLaunchState {
    data class Launch(val state: VROTopBarState) : VROTopBarLaunchState()
    data class Clear(val state: VROTopBarState? = null) : VROTopBarLaunchState()
}

sealed class VROBottomBarLaunchState {
    data class Launch(val state: VROBottomBarState) : VROBottomBarLaunchState()
    data class Clear(val state: VROBottomBarState? = null) : VROBottomBarLaunchState()
}