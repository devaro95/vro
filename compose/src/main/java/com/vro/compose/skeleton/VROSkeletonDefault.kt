package com.vro.compose.skeleton

import androidx.compose.runtime.Composable

class VROSkeletonDefault : VROSkeleton() {
    @Composable
    override fun SkeletonContent() = Unit

    @Composable
    override fun SkeletonPreview() = Unit
}