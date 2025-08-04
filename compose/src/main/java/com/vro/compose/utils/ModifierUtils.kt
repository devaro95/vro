/**
 * Utility functions for Compose modifiers with device-specific behavior and scrolling.
 */
package com.vro.compose.utils

import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager

/**
 * Applies the given modifier only on tablet devices.
 *
 * @param modifier The modifier to apply when device is a tablet
 * @return The original modifier unchanged if not on tablet,
 *         or the original modifier combined with the provided modifier if on tablet
 */
@Composable
fun Modifier.tabletModifier(modifier: Modifier.() -> Modifier): Modifier {
    return if (isTablet())
        this.then(modifier.invoke(this))
    else this
}

/**
 * Applies the given modifier only on mobile (non-tablet) devices.
 *
 * @param modifier The modifier to apply when device is a mobile
 * @return The original modifier unchanged if on tablet,
 *         or the original modifier combined with the provided modifier if on mobile
 */
@Composable
fun Modifier.mobileModifier(modifier: Modifier.() -> Modifier): Modifier {
    return if (!isTablet())
        this.then(modifier.invoke(this))
    else this
}

/**
 * Adds vertical scroll behavior to a composable with optional focus clearing.
 *
 * @param removeFocus If true, clears focus when scrolling begins (default false)
 * @return A modifier with vertical scroll behavior
 */
@Composable
fun Modifier.vroVerticalScroll(removeFocus: Boolean = false): Modifier {
    val scrollState = rememberScrollState()
    if (scrollState.isScrollInProgress && removeFocus) LocalFocusManager.current.clearFocus()
    return this.verticalScroll(scrollState)
}