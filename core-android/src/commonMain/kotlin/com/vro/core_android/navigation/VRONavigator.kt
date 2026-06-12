package com.vro.core_android.navigation

import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination

/**
 * Platform-agnostic navigator interface.
 * Android implementation uses NavController; iOS uses its own routing.
 */
interface VRONavigator<D : VRODestination> {

    fun onDestination(destination: D)

    fun navigateBack(result: VROBackResult?)

    fun finish()

    companion object {
        const val NAVIGATION_STATE = "NAVIGATION_STATE"
    }
}
