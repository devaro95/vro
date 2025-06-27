package com.vro.compose.skeleton

import androidx.compose.runtime.Composable

/**
 * Default implementation of [VROSkeleton] that renders no content.
 *
 * This can be used as a fallback when no loading or preview UI is needed
 * for a particular screen or template.
 */
class VROSkeletonDefault : VROSkeleton() {

    /**
     * Empty implementation of the skeleton content.
     * Override this method to show a loading state while the real content is fetched or prepared.
     */
    @Composable
    override fun SkeletonContent() = Unit

    /**
     * Empty implementation of the skeleton preview.
     * Override this method to display a visual placeholder (e.g., for previews in tooling).
     */
    @Composable
    override fun SkeletonPreview() = Unit
}