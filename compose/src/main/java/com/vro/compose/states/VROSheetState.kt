package com.vro.compose.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

class VROSheetState {
    private var hideActionListener: (() -> Unit)? = null

    @Composable
    internal fun setHideActionListener(action: () -> Unit) {
        hideActionListener = action
    }

    fun hide() {
        hideActionListener?.invoke()
    }
}

@Composable
fun rememberVROSheetState(): VROSheetState {
    return remember { VROSheetState() }
}