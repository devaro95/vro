package com.vro.navigation

import java.io.Serializable

interface VRONavigator<D : VRODestination> {
    fun navigate(destination: D)
    fun navigateBack(result: Serializable?)
    fun finish()
}