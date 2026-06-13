package com.vro.compose.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberSheetState(): SheetStateVro = remember { SheetStateVro() }

class SheetStateVro {
    private var hideActionListener: (() -> Unit)? = null

    @Composable
    internal fun setHideActionListener(action: () -> Unit) {
        hideActionListener = action
    }

    fun hide() { hideActionListener?.invoke() }
}
