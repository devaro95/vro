package com.vro.compose.states

sealed class VROSheetState {
    data object Show : VROSheetState()
    data object Hide : VROSheetState()
}
