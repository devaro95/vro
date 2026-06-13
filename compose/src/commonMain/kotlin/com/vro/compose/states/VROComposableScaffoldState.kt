package com.vro.compose.states

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

sealed class VROTopBarBaseState(open val visibility: Boolean = false) {
    data class VROTopBarStartState(override val visibility: Boolean = false) : VROTopBarBaseState(visibility)
    open class VROTopBarState(
        override val visibility: Boolean = true,
        open val title: (@Composable () -> Unit)? = null,
        open val navigationButton: (@Composable () -> Unit)? = null,
        open val actionButton: (@Composable RowScope.() -> Unit)? = null,
        open val background: Color? = null,
        open val height: Dp? = null,
    ) : VROTopBarBaseState(visibility)
}

sealed class VROBottomBarBaseState(open val visibility: Boolean = false) {
    data class VROBottomBarStartState(override val visibility: Boolean = false) : VROBottomBarBaseState(visibility)
    open class VROBottomBarState(
        override val visibility: Boolean = true,
        open val selectedItem: Int = 0,
    ) : VROBottomBarBaseState(visibility)
}
