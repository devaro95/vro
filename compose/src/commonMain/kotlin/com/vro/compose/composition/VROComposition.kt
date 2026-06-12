package com.vro.compose.composition

import androidx.compose.animation.*
import androidx.compose.runtime.*
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope> { error("No SharedTransitionScope provided") }

val LocalAnimatedVisibilityScope = staticCompositionLocalOf<AnimatedVisibilityScope> { error("No AnimatedVisibilityScope provided") }

val LocalTopBarState = compositionLocalOf<MutableState<VROTopBarBaseState>> { mutableStateOf(VROTopBarStartState()) }

val LocalBottomBarState = compositionLocalOf<MutableState<VROBottomBarBaseState>> { mutableStateOf(VROBottomBarStartState()) }

val LocalSnackbarState = compositionLocalOf<MutableState<VROSnackBarState>> { error("No VROSnackBarState provided") }
