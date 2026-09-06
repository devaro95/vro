package com.vro.compose.composition

import androidx.compose.animation.*
import androidx.compose.runtime.*
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope> {  error("No se ha proporcionado SharedTransitionScope") }

val LocalAnimatedVisibilityScope = staticCompositionLocalOf<AnimatedVisibilityScope> {  error("No se ha proporcionado AnimatedVisibilityScope") }

val LocalTopBarState = compositionLocalOf<MutableState<VROTopBarBaseState>> { error("No se ha proporcionado VROTopBarBaseState")  }

val LocalBottomBarState = compositionLocalOf<MutableState<VROBottomBarBaseState>> { error("No se ha proporcionado VROBottomBarBaseState")  }

val LocalSnackbarState = compositionLocalOf<MutableState<VROSnackBarState>> { error("No se ha proporcionado VROSnackBarState") }

/**
 * Normalized scroll signal (0f..1f) for a floating BottomBar that opted in via
 * [com.vro.compose.VROComposableActivity.bottomBarHidesOnScroll]: 0f means the user is at rest or
 * scrolling up, 1f means scrolled down past the collapse threshold. Defaults to 0f so reading it
 * outside a provider (feature disabled, or no BottomBar in this screen) is always safe. How this
 * value is used -- hide, shrink, ignore -- is entirely up to the BottomBar() implementation.
 */
val LocalBottomBarScrollProgress = compositionLocalOf { 0f }