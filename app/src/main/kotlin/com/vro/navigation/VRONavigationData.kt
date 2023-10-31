package com.vro.navigation

import java.io.Serializable

data class VRONavigationData<D: VRODestination>(
    val destination: D? = null,
    val state: Serializable? = null,
    val anim: NavAnim? = null,
    val navigateBack: Boolean = false,
    val backResult: Serializable? = null,
) : Serializable {
    data class NavAnim(
        val enterAnim: Int? = null,
        val exitAnim: Int? = null,
        val popEnterAnim: Int? = null,
        val popExitAnim: Int? = null,
    )
}