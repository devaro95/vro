package com.vro.compose.composition

import androidx.compose.animation.*
import androidx.compose.runtime.*
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope> { error("No SharedTransitionScope provided") }

val LocalAnimatedVisibilityScope = staticCompositionLocalOf<AnimatedVisibilityScope> { error("No AnimatedVisibilityScope provided") }

val LocalTopBarState = compositionLocalOf<MutableState<VROTopBarBaseState>> { error("No VROTopBarBaseState provided") }

val LocalBottomBarState = compositionLocalOf<MutableState<VROBottomBarBaseState>> { error("No VROBottomBarBaseState provided") }

val LocalSnackbarState = compositionLocalOf<MutableState<VROSnackBarState>> { error("No VROSnackBarState provided") }
