package com.vro.state

class VROTopBarState(
    val actionButton: VROTopBarButton? = null,
    val navigationButton: VROTopBarButton? = null,
) {
    data class VROTopBarButton(
        val icon: Int,
        val onClick: () -> Unit,
    )
}