package com.vro.compose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vro.compose.states.VROComposableScaffoldState.VROBottomBarState.VROBottomBarItem

@Composable
fun VROBottomBar(
    itemList: List<VROBottomBarItem> = emptyList(),
    height: Dp = 55.dp,
) {
    BottomAppBar(
        containerColor = Color(0xff01040b),
        modifier = Modifier.height(height),
        tonalElevation = 0.dp
    ) {
        var selected by remember { mutableIntStateOf(0) }
        Row(modifier = Modifier.fillMaxWidth()) {
            itemList.forEachIndexed { index, item ->
                AnimatedIcon(
                    iconRes = item.icon,
                    modifier = Modifier.weight(1f),
                    selected = selected == index
                ) {
                    selected = index
                    item.onClick?.invoke()
                }
            }
        }
    }
}

@Composable
private fun AnimatedIcon(
    iconRes: Int,
    modifier: Modifier = Modifier,
    iconSize: Dp = 25.dp,
    selectedScale: Float = 1.2f,
    unselectedColor: Color = MaterialTheme.colorScheme.primary,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    val animatedScale: Float by animateFloatAsState(
        targetValue = if (selected) selectedScale else 1f,
        animationSpec = TweenSpec(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "animatedScale"
    )
    val animatedColor by animateColorAsState(
        targetValue = if (selected) selectedColor else unselectedColor,
        animationSpec = TweenSpec(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "animatedColor"
    )
    Icon(
        painter = painterResource(id = iconRes),
        contentDescription = "",
        tint = animatedColor,
        modifier = modifier
            .size(iconSize)
            .scale(animatedScale)
            .clickable(
                onClick = { onClick.invoke() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    )
}
