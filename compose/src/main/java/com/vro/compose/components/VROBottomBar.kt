/**
 * Package containing bottom navigation bar components.
 */
package com.vro.compose.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vro.constants.EMPTY_STRING

/**
 * A customizable bottom navigation bar component.
 *
 * Features:
 * - Animated icon transitions
 * - Customizable appearance
 * - Flexible item configuration
 * - Selection state management
 *
 * @param modifier Modifier for styling the bar
 * @param itemList List of navigation items (default: empty)
 * @param height Height of the bar (default: 55.dp)
 * @param background Background color (default: Black)
 * @param selectedItem Index of the currently selected item
 *
 * @see VROBottomBarItem For item configuration
 */
@Composable
fun VROBottomBar(
    modifier: Modifier = Modifier,
    itemList: List<VROBottomBarItem> = emptyList(),
    height: Dp = 55.dp,
    background: Color = Color.Black,
    selectedItem: Int,
) {
    BottomAppBar(
        containerColor = background,
        modifier = modifier
            .height(height)
            .fillMaxWidth(),
        tonalElevation = 0.dp
    ) {
        Crossfade(targetState = selectedItem, label = EMPTY_STRING) { iconSelected ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemList.forEachIndexed { index, item ->
                    AnimatedIcon(
                        iconRes = item.icon,
                        modifier = Modifier.weight(1f),
                        selected = iconSelected == index,
                        selectedIconRes = item.iconSelected ?: item.icon,
                        iconTint = item.iconSelectedTint?.let { iconSelectedTint ->
                            if (iconSelected == index) iconSelectedTint
                            else item.iconTint
                        } ?: item.iconTint,
                        iconSize = item.iconSize
                    ) {
                        item.onClick?.invoke()
                    }
                }
            }
        }
    }
}

/**
 * Configuration class for bottom navigation bar items.
 *
 * @property icon Resource ID for the default icon
 * @property iconTint Color for the default icon (default: null)
 * @property iconSelectedTint Color for the selected icon (default: null)
 * @property iconSelected Resource ID for the selected icon (default: null)
 * @property contentDescription Accessibility content description (default: "")
 * @property text Optional text label (default: "")
 * @property iconSize Size of the icons (default: 24.dp)
 * @property onClick Callback when item is clicked (default: null)
 *
 */
open class VROBottomBarItem(
    val icon: Int,
    val iconTint: Color? = null,
    val iconSelectedTint: Color? = null,
    val iconSelected: Int? = null,
    val contentDescription: String = EMPTY_STRING,
    val text: String = EMPTY_STRING,
    val iconSize: Dp = 24.dp,
    val onClick: (() -> Unit)? = null,
)

/**
 * Internal composable that handles icon animation and selection state.
 *
 * @param iconRes Resource ID for the default icon
 * @param selectedIconRes Resource ID for the selected icon
 * @param modifier Modifier for styling
 * @param iconSize Size of the icon (default: 25.dp)
 * @param selected Whether the item is selected (default: false)
 * @param iconTint Color tint for the icon (default: null)
 * @param onClick Callback when icon is clicked
 */
@Composable
private fun AnimatedIcon(
    iconRes: Int,
    selectedIconRes: Int,
    modifier: Modifier = Modifier,
    iconSize: Dp = 25.dp,
    selected: Boolean = false,
    iconTint: Color?,
    onClick: () -> Unit,
) {
    if (selected) {
        Icon(
            painter = painterResource(id = selectedIconRes),
            contentDescription = EMPTY_STRING,
            modifier = modifier
                .size(iconSize)
                .clickable(
                    onClick = { onClick() },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            tint = iconTint ?: LocalContentColor.current
        )
    } else {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = EMPTY_STRING,
            modifier = modifier
                .size(iconSize)
                .clickable(
                    onClick = { onClick() },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            tint = iconTint ?: LocalContentColor.current
        )
    }
}