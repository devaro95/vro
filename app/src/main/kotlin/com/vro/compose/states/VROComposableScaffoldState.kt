package com.vro.compose.states

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vro.constants.EMPTY_STRING

data class VROComposableScaffoldState(
    val topBarState: VROTopBarState? = null,
    val showBottomBar: Boolean = true,
) {
    data class VROTopBarState(
        val title: String = EMPTY_STRING,
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

    open class VROBottomBarState(
        val height: Dp,
        val background: Color,
        val itemList: List<VROBottomBarItem>,
    ) {
        open class VROBottomBarItem(
            open val icon: Int,
            open val iconTint: Color? = null,
            val iconSelected: Int? = null,
            val contentDescription: String = EMPTY_STRING,
            val text: String = EMPTY_STRING,
            val iconSize: Dp = 24.dp,
            val onClick: (() -> Unit)? = null,
        )
    }
}