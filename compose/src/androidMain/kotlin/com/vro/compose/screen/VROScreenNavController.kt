package com.vro.compose.screen

import androidx.navigation.NavController

/**
 * Android-specific extension property for NavController on VROScreenBase.
 * Stored as a companion/tag pattern to avoid breaking commonMain API.
 */
private val navControllers = HashMap<String, NavController>()

var <S : com.vro.state.VROState, E : com.vro.event.VROEvent> VROScreenBase<S, E>.navController: NavController
    get() = navControllers[this::class.simpleName] ?: error("NavController not initialized for ${this::class.simpleName}")
    set(value) { navControllers[this::class.simpleName!!] = value }
