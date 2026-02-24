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