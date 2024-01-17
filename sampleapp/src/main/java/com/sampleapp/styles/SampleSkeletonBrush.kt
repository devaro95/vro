package com.sampleapp.styles

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.vro.compose.skeleton.vroSkeletonBrush

@Composable
fun sampleSkeletonBrush(): Brush {
    return vroSkeletonBrush(
        color = Color.Cyan
    )
}