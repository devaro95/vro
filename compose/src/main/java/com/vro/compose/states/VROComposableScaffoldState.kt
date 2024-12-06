package com.vro.compose.states

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vro.constants.INT_ZERO

sealed class VROTopBarBaseState(
    open val visibility: Boolean = true,
) {
    data class VROTopBarState(
        val title: @Composable (() -> Unit)? = null,
        val actionButton: @Composable (RowScope.() -> Unit)? = null,
        val navigationButton: @Composable (() -> Unit)? = null,
        val height: Dp? = null,
        val background: Color? = null,
        override val visibility: Boolean = true,
    ) : VROTopBarBaseState()

    data class VROTopBarStartState(
        val title: @Composable (() -> Unit)? = null,
        val actionButton: @Composable (RowScope.() -> Unit)? = null,
        val navigationButton: @Composable (() -> Unit)? = null,
        val height: Dp? = null,
        val background: Color? = null,
        override val visibility: Boolean = false,
    ) : VROTopBarBaseState()

    data class VROTopBarButton(
        val icon: Int? = null,
        val iconVector: ImageVector? = null,
        val onClick: () -> Unit,
        val tint: Color? = null,
        val iconSize: Dp = 30.dp,
    )
}

sealed class VROBottomBarBaseState(
    open val visibility: Boolean = true,
) {
    data class VROBottomBarState(
        val selectedItem: Int = INT_ZERO,
        override val visibility: Boolean = true,
    ) : VROBottomBarBaseState()

    data class VROBottomBarStartState(
        val selectedItem: Int = INT_ZERO,
        override val visibility: Boolean = false,
    ) : VROBottomBarBaseState()
}

