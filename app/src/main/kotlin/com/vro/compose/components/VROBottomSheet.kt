package com.vro.compose.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.launch

@Composable
fun rememberSheetState(): SheetStateVro {
    return remember { SheetStateVro() }
}

class SheetStateVro {
    private var hideActionListener: (() -> Unit)? = null

    @Composable
    internal fun setHideActionListener(action: () -> Unit) {
        hideActionListener = action
    }

    fun hide() {
        hideActionListener?.invoke()
    }
}

@ExperimentalMaterial3Api
@Composable
fun VROBottomSheet(
    modifier: Modifier = Modifier,
    height: Dp,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    fullExpanded: Boolean = false,
    onDismiss: () -> Unit,
    content: @Composable (SheetStateVro) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = !fullExpanded)
    val sheetStateVro = rememberSheetState()
    sheetStateVro.setHideActionListener {
        coroutineScope.launch {
            sheetState.hide()
            onDismiss.invoke()
        }
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = shape,
        modifier = Modifier
            .height(height)
            .then(modifier),
        containerColor = containerColor
    ) {
        content.invoke(sheetStateVro)
    }
}
