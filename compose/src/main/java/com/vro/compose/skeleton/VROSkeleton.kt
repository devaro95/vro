package com.vro.compose.skeleton

import androidx.compose.runtime.Composable

abstract class VROSkeleton {
    @Composable
    abstract fun SkeletonContent()

    @Composable
    abstract fun SkeletonPreview()
}