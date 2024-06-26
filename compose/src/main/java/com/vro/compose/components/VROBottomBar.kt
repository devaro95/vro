package com.vro.compose.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vro.compose.states.VROComposableScaffoldState.VROBottomBarState.VROBottomBarItem
import com.vro.constants.EMPTY_STRING

@Composable
fun VROBottomBar(
    modifier: Modifier = Modifier,
    itemList: List<VROBottomBarItem> = emptyList(),
    height: Dp = 55.dp,
    background: Color,
    selectedItem: MutableIntState,
) {
    BottomAppBar(
        containerColor = background,
        modifier = modifier
            .height(height)
            .fillMaxWidth(),
        tonalElevation = 0.dp
    ) {
        Crossfade(targetState = selectedItem.intValue, label = EMPTY_STRING) { iconSelected ->
            Row(modifier = Modifier.fillMaxWidth()) {
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
                        selectedItem.intValue = index
                        item.onClick?.invoke()
                    }
                }
            }
        }
    }
}

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
                    onClick = { onClick.invoke() },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            tint = iconTint ?: Color.White
        )
    } else {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = EMPTY_STRING,
            modifier = modifier
                .size(iconSize)
                .clickable(
                    onClick = { onClick.invoke() },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            tint = iconTint ?: Color.White
        )
    }
}