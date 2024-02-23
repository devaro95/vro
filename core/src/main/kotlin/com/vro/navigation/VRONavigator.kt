package com.vro.navigation

interface VRONavigator<D : VRODestination> {
    fun navigate(destination: D)
    fun navigateBack(result: VROBackResult?)
    fun finish()
}