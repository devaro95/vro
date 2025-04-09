package com.vro.compose.skeleton

import androidx.compose.runtime.Composable

/**
 * Abstract base class for creating skeleton loading components.
 *
 * Provides the contract for skeleton UI implementations that show placeholder content
 * while data is loading. Concrete implementations must provide both the loading state
 * content and a preview variant.
 *
 * ## Usage
 * 1. Extend this class to create specific skeleton implementations
 * 2. Override [SkeletonContent] for the actual loading placeholder
 * 3. Override [SkeletonPreview] to demonstrate the skeleton in previews
 *
 * @see VROTextSkeleton For a rectangular text placeholder implementation
 * @see VROButtonSkeleton For a circular button placeholder implementation
 */
abstract class VROSkeleton {
    /**
     * Composable function that renders the skeleton loading UI.
     * This should display the placeholder content that mimics the real UI structure.
     */
    @Composable
    abstract fun SkeletonContent()

    /**
     * Composable function that renders a preview of the skeleton loading UI.
     * This should demonstrate how the skeleton appears in design previews.
     */
    @Composable
    abstract fun SkeletonPreview()
}