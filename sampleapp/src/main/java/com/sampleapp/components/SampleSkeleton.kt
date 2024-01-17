package com.sampleapp.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sampleapp.styles.sampleSkeletonBrush
import com.vro.compose.skeleton.VROButtonSkeleton
import com.vro.compose.skeleton.VROTextSkeleton

@Composable
fun SampleTextSkeleton(
    modifier: Modifier = Modifier,
    width: Dp = 15.dp,
    height: Dp = 15.dp,
    skeletonBrush: Brush = sampleSkeletonBrush(),
) {
    VROTextSkeleton(
        modifier,
        width,
        height,
        skeletonBrush
    )
}

@Composable
fun SampleButtonSkeleton(
    modifier: Modifier = Modifier,
    width: Dp = 40.dp,
    height: Dp = 40.dp,
    skeletonBrush: Brush = sampleSkeletonBrush(),
) {
    VROButtonSkeleton(
        modifier,
        width,
        height,
        skeletonBrush
    )
}