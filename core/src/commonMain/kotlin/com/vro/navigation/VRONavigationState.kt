package com.vro.navigation

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class VRONavigationState<D : VRODestination>(
    val destination: D? = null,
    @Contextual
    val state: @Serializable Any? = null,
    val anim: NavAnim? = null,
    val backResult: VROBackResult? = null,
) {
    data class NavAnim(
        val enterAnim: Int? = null,
        val exitAnim: Int? = null,
        val popEnterAnim: Int? = null,
        val popExitAnim: Int? = null,
    )
}