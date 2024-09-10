package com.vro.compose.states

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vro.constants.INT_ZERO

data class VROTopBarState(
    val title: @Composable (() -> Unit)? = null,
    val actionButton: @Composable (RowScope.() -> Unit)? = null,
    val navigationButton: @Composable (() -> Unit)? = null,
    val height: Dp? = null,
    val background: Color? = null,
) {
    data class VROTopBarButton(
        val icon: Int? = null,
        val iconVector: ImageVector? = null,
        val onClick: () -> Unit,
        val tint: Color? = null,
        val iconSize: Dp = 30.dp,
    )
}

open class VROBottomBarState(
    val selectedItem: Int = INT_ZERO,
)