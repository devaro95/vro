package com.vro.compose.states

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.*
import com.vro.constants.EMPTY_STRING
import com.vro.constants.INT_ZERO

data class VROTopBarState(
    val title: String = EMPTY_STRING,
    val titleColor: Color? = null,
    val actionButton: VROTopBarButton? = null,
    val navigationButton: VROTopBarButton? = null,
    val titleSize: TextUnit = 16.sp,
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