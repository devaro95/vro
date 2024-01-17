package com.vro.compose.states

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class VROComposableScaffoldState(
    val topBarState: VROTopBarState? = null,
    val bottomBarState: VROBottomBarState? = null,
) {
    data class VROTopBarState(
        val title: String = "",
        val actionButton: VROTopBarButton? = null,
        val navigationButton: VROTopBarButton? = null,
    ) {
        data class VROTopBarButton(
            val icon: Int? = null,
            val iconVector: ImageVector? = null,
            val onClick: () -> Unit,
            val tint: Color? = null,
            val iconSize: Dp = 30.dp,
        )
    }

    data class VROBottomBarState(
        val itemList: List<VROBottomBarItem> = emptyList(),
        val height: Dp = 55.dp,
    ) {
        data class VROBottomBarItem(
            val icon: Int,
            val contentDescription: String = "",
            val text: String = "",
            val iconSize: Dp = 24.dp,
            val onClick: (() -> Unit)? = null,
        )
    }
}