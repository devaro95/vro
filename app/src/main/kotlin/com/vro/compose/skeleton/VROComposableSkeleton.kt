package com.vro.compose.skeleton

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vro.constants.FLOAT_ZERO

@Composable
fun VROTextSkeleton(
    modifier: Modifier = Modifier,
    width: Dp = 15.dp,
    height: Dp = 15.dp,
    skeletonBrush: Brush = vroSkeletonBrush(),
) {
    Column(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .width(width)
                .height(height)
                .clip(RectangleShape)
                .background(skeletonBrush)
        )
    }
}

@Composable
fun VROButtonSkeleton(
    modifier: Modifier = Modifier,
    width: Dp = 40.dp,
    height: Dp = 40.dp,
    skeletonBrush: Brush = vroSkeletonBrush(),
) {
    Column(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .width(width)
                .height(height)
                .clip(CircleShape)
                .background(skeletonBrush)
        )
    }
}

@Composable
fun vroSkeletonBrush(
    color: Color = Color.White,
    repeatMode: RepeatMode = RepeatMode.Reverse,
): Brush {
    val shimmerColors = listOf(
        color.copy(alpha = 0.7f),
        color.copy(alpha = 0.55f),
        color.copy(alpha = 0.7f),
    )
    val transition = rememberInfiniteTransition(label = "transition")
    val translateAnimation = transition.animateFloat(
        initialValue = FLOAT_ZERO,
        targetValue = 900f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = repeatMode,
        ),
        label = "translateAnimation"
    )
    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = FLOAT_ZERO)
    )
}