package com.vro.navigation

import com.vro.state.VROState
import java.io.Serializable

interface VRONavigator<D : VRODestination> {
    fun navigate(destination: D)
    fun navigateBack(result: Serializable?)
    fun navigateWithAction(action: Int, state: VROState? = null)
    fun finish()
}