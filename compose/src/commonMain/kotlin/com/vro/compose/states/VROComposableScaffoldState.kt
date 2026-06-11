package com.vro.compose.states

import androidx.compose.ui.graphics.Color

sealed class VROTopBarBaseState(open val visibility: Boolean = false) {
    data class VROTopBarStartState(override val visibility: Boolean = false) : VROTopBarBaseState(visibility)
    open class VROTopBarState(
        override val visibility: Boolean = true,
        open val title: String = "",
        open val navigationIcon: Any? = null,
        open val onNavigationClick: (() -> Unit)? = null,
        open val backgroundColor: Color? = null,
        open val titleColor: Color? = null,
    ) : VROTopBarBaseState(visibility)
}

sealed class VROBottomBarBaseState(open val visibility: Boolean = false) {
    data class VROBottomBarStartState(override val visibility: Boolean = false) : VROBottomBarBaseState(visibility)
    open class VROBottomBarState(
        override val visibility: Boolean = true,
        open val selectedItem: Int = 0,
    ) : VROBottomBarBaseState(visibility)
}
