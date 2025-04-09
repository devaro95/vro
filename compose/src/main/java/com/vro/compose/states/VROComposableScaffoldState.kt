/**
 * State classes for managing top and bottom app bar configurations in Compose UI.
 */
package com.vro.compose.states

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vro.constants.INT_ZERO

/**
 * Sealed class hierarchy representing different states of a top app bar.
 *
 * @property visibility Controls whether the top bar is visible (default true)
 */
sealed class VROTopBarBaseState(
    open val visibility: Boolean = true,
) {
    /**
     * Complete top bar configuration state with all customizable properties.
     *
     * @property title Composable function for the title content
     * @property actionButton Composable function for action buttons in the row scope
     * @property navigationButton Composable function for the navigation button
     * @property height Height of the top bar
     * @property background Background color of the top bar
     * @property visibility Controls whether the top bar is visible (default true)
     */
    data class VROTopBarState(
        val title: @Composable (() -> Unit)? = null,
        val actionButton: @Composable (RowScope.() -> Unit)? = null,
        val navigationButton: @Composable (() -> Unit)? = null,
        val height: Dp? = null,
        val background: Color? = null,
        override val visibility: Boolean = true,
    ) : VROTopBarBaseState()

    /**
     * Initial state for the top bar, typically used when the bar should be hidden.
     *
     * @property title Composable function for the title content
     * @property actionButton Composable function for action buttons
     * @property navigationButton Composable function for the navigation button
     * @property height Height of the top bar
     * @property background Background color of the top bar
     * @property visibility Controls whether the top bar is visible (default false)
     */
    data class VROTopBarStartState(
        val title: @Composable (() -> Unit)? = null,
        val actionButton: @Composable (RowScope.() -> Unit)? = null,
        val navigationButton: @Composable (() -> Unit)? = null,
        val height: Dp? = null,
        val background: Color? = null,
        override val visibility: Boolean = false,
    ) : VROTopBarBaseState()

    /**
     * Configuration for a single button in the top app bar.
     *
     * @property icon Resource ID for the button icon
     * @property iconVector Vector image for the button icon
     * @property onClick Callback when the button is clicked
     * @property tint Color tint for the icon
     * @property iconSize Size of the icon (default 30.dp)
     */
    data class VROTopBarButton(
        val icon: Int? = null,
        val iconVector: ImageVector? = null,
        val onClick: () -> Unit,
        val tint: Color? = null,
        val iconSize: Dp = 30.dp,
    )
}

/**
 * Sealed class hierarchy representing different states of a bottom app bar.
 *
 * @property visibility Controls whether the bottom bar is visible (default true)
 */
sealed class VROBottomBarBaseState(
    open val visibility: Boolean = true,
) {
    /**
     * Complete bottom bar configuration state.
     *
     * @property selectedItem Index of the currently selected item
     * @property visibility Controls whether the bottom bar is visible (default true)
     */
    data class VROBottomBarState(
        val selectedItem: Int = INT_ZERO,
        override val visibility: Boolean = true,
    ) : VROBottomBarBaseState()

    /**
     * Initial state for the bottom bar, typically used when the bar should be hidden.
     *
     * @property selectedItem Index of the currently selected item
     * @property visibility Controls whether the bottom bar is visible (default false)
     */
    data class VROBottomBarStartState(
        val selectedItem: Int = INT_ZERO,
        override val visibility: Boolean = false,
    ) : VROBottomBarBaseState()
}